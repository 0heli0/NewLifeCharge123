package com.newlife.charge.service.impl;


import com.google.common.collect.Lists;
import com.newlife.charge.common.CommonUtil;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.common.TelCharg.TelChargApi;
import com.newlife.charge.common.weixin.WeiXinUtils;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.FormulaModeGeneralOut;
import com.newlife.charge.core.dto.out.FormulaModeOut;
import com.newlife.charge.core.dto.out.UserAccountOut;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.WeiRefundService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 车主端 微信退款管理
 *
 */
@Service
@Transactional
public class WeiRefundServiceImpl implements WeiRefundService {


    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";

    @Value("${commonService.path}")
    private String commonServicePath;

    @Value("${newLife.charge.ip}")
    private String ip;

    @Value("${pay.isReal}")
    private boolean isReal;

    private Logger logger = LoggerFactory.getLogger(WeiRefundServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private RefundLogMapper refundLogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserWeixinMapper userWeixinMapper;

    @Autowired
    private UserAccountMapper accountMapper;

    @Autowired
    private NewLifeSpendLogMapper newLifeSpendLogMapper;

    @Autowired
    private WeixinRefundLogMapper weixinRefundLogMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public Order createOrder(WRefindCreateOrderIn orderIn, Integer userId, String type) {

        Lock lock = new ReentrantLock(false);
        logger.info("===> 添加锁");
        lock.lock();
        try {
            Date date = new Date();
            // 生成新的订单号
            String orderSn = TelChargApi.getNewOrderSn(type);

            // 查看用户是否绑定了微信
            UserWeixin weixin = userWeixinMapper.getByUserId(userId);

            if(weixin == null){
                logger.info("===> 用户未绑定微信，不能退款");
                throw new BizException(ERROR.NOT_WEI_XIN_USER);
            }

            // 判断退款金额是否满足退款条件
            UserAccount userAccount = this.userAccountMapper.getByUserId(userId);
            if(userAccount == null){
                logger.info("===> 用户不存在账户信息，不能退款");
                throw new BizException(ERROR.DATA_NOT_FOUND);
            }
            if(userAccount.getChargeBalance().compareTo(orderIn.getPrice()) < 0){
                throw new BizException(ERROR.USER_BALANCE_ERROR);
            }

            // 当返回成功后，保存订单
            Order order = new Order();
            order.setOrderSn(orderSn);
            order.setUserId(userId);
            order.setTotalPrice(orderIn.getPrice());
            order.setSumPrice(orderIn.getPrice());
            order.setCouponPrice(new BigDecimal(0));
            order.setOrderType(OrderTypeEnum.REFUND.getValue());
            order.setPayType(PayTypeEnum.WEIXIN.getValue());
            order.setPayStatus(PayStatusEnum.NONE.getValue());
            order.setPayTime(date);
            order.setCreateTime(date);
            orderMapper.insert(order);

            // 生成退款记录并将退款记录和订单绑定
            /*RefundLog refundLog = new RefundLog();
            refundLog.setOrderId(order.getId());
            refundLog.setOrderSn(orderSn);
            refundLog.setPaymentSn("");
            refundLog.setUserId(userId);
            refundLog.setTotalPrice(order.getTotalPrice());
            refundLog.setPayTime(new Date());
            refundLog.setRefundStatus(0);
            refundLog.setCreateTime(new Date());

            refundLogMapper.insert(refundLog);*/
            return order;
        }finally {
            lock.unlock();
            logger.info("===> 释放锁");
        }

    }

    @Override
    public WeiRefundResult weiChatRefund(WeiRefundIn refundIn, Integer userId) {

        Lock lock = new ReentrantLock(false);
        WeiRefundResult weiRefundResult = new WeiRefundResult();
        // 先查询该订单是否
        logger.info("===> 添加锁");
        lock.lock();
        try {
            // 将金额转换为分
            int price = refundIn.getAmount().multiply(WeiXinUtils.switchToCent).intValue();
            if(!isReal){
                logger.info("===> 模拟环境");
                price = 100;
            }
            String type = "";

            String nonceStr = StringUtil.getUUId();

            // 构造参数类
            WeiRefundParams weiRefundParams = new WeiRefundParams();
            weiRefundParams.setMchAppId(WeiXinUtils.APP_Id);
            weiRefundParams.setMchId(WeiXinUtils.MCH_Id);
            weiRefundParams.setNonceStr(nonceStr);
            weiRefundParams.setPartnerTradeNo(refundIn.getOrderSn());
            weiRefundParams.setOpenid(refundIn.getOpenid());
            weiRefundParams.setCheckName("NO_CHECK");
            weiRefundParams.setAmount(price);
            weiRefundParams.setDesc("退款");
            weiRefundParams.setSpbillCreateIp(ip);

            // 生成签名
            String str = WeiXinUtils.createSign(WeiRefundParams.class, weiRefundParams);
            String stringSignTemp=str+"&key="+ WeiXinUtils.APP_KEY;
            weiRefundParams.setSign(stringSignTemp);

            // md5加密
            logger.info("===> stringSignTemp:"+str);
            logger.info("===> stringSignTemp:"+stringSignTemp);
            String md5 = new Md5Hash(stringSignTemp).toString().toUpperCase();
            weiRefundParams.setSign(md5);

            // 生成xml
            String params = CommonUtil.toXml(WeiRefundParams.class, weiRefundParams);

            String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

            logger.info("===> params:\b"+params);

            String result = doRefund(WeiXinUtils.MCH_Id, url ,params);

            logger.info("===> result: "+result);

            // 解析返回数据
            weiRefundResult = (WeiRefundResult)CommonUtil.toBean(WeiRefundResult.class, result);
            logger.info("===> weiRefundResult:"+weiRefundResult);

            if(weiRefundResult == null){
                logger.info("===> 没有退款数据");
                throw new BizException(ERROR.DATA_NOT_FOUND);
            }

            // 请求失败
            if(FAIL.equalsIgnoreCase(weiRefundResult.getReturnCode())){
                logger.info("===> 退款请求失败");
                throw new BizException(ERROR.DATA_NOT_FOUND.code(), weiRefundResult.getReturnMsg());
            }

            type = SUCCESS;
            // 请求成功，返回结果为失败
            if(FAIL.equalsIgnoreCase(weiRefundResult.getResultCode())){
                // 修改订单状态为失败，并将用户余额冻结，因为可能是因为延迟造成，避免造成二次退款，并生成用户退款记录
                type = FAIL;
                logger.info("===> 退款失败");
                weiRefundResult.setPartnerTradeNo("");
                weiRefundResult.setPaymentNo("");
                weiRefundResult.setPaymentTime("");
            }else {
                weiRefundResult.setErrCode("");
                weiRefundResult.setErrCodeDes("");
            }
            logger.info("===> 退款成功，开始保存记录");


            // 保存微信退款记录
            WeixinRefundLog weiXinRefundLog = dozer.map(weiRefundResult, WeixinRefundLog.class);
            weiXinRefundLog.setOpenid(refundIn.getOpenid());
            weiXinRefundLog.setAppid(WeiXinUtils.APP_Id);
            weiXinRefundLog.setCreateTime(new Date());
            logger.info("===> weiXinRefundLog:"+weiXinRefundLog.toString());

            // 将可能为空的值，设置默认值
            if(weiXinRefundLog.getDeviceInfo() == null){
                weiXinRefundLog.setDeviceInfo("");
            }
            if(weiXinRefundLog.getErrCode() == null){
                weiXinRefundLog.setErrCode("");
            }
            if(weiXinRefundLog.getErrCodeDes() == null){
                weiXinRefundLog.setErrCodeDes("");
            }
            if(weiXinRefundLog.getReturnMsg() == null){
                weiXinRefundLog.setReturnMsg("");
            }
            if(weiXinRefundLog.getSign() == null){
                weiXinRefundLog.setSign("");
            }
            if(weiXinRefundLog.getSignType() == null){
                weiXinRefundLog.setSignType("");
            }
            if(weiXinRefundLog.getAttach() == null){
                weiXinRefundLog.setAttach("");
            }
            if(weiXinRefundLog.getNonceStr() == null){
                weiXinRefundLog.setNonceStr("");
            }
            if(weiXinRefundLog.getMchAppid() == null){
                weiXinRefundLog.setMchAppid("");
            }
            if(weiXinRefundLog.getMchId() == null){
                weiXinRefundLog.setMchId("");
            }

            weixinRefundLogMapper.insert(weiXinRefundLog);

            // 操作成功，则修改订单状态为成功，生成用户退款记录，生成新活资金流水记录
            logger.info("===> weiRefundResult");
            weiRefundHandle(weiRefundResult,refundIn,userId,type);

            return weiRefundResult;

        }catch (Exception exp)
        {
            logger.info("===> 退款失败,请查看退款结果："+weiRefundResult.toString());
            throw new BizException(exp);
        }finally {
            lock.unlock();
            logger.info("===> 释放锁");
        }
//        return weiRefundResult;
    }

    /**
     * 调用接口成功后操作
     * @param weiRefundResult
     * @param refundIn
     * @param userId
     * @param type
     */
    private void weiRefundHandle(WeiRefundResult weiRefundResult, WeiRefundIn refundIn, Integer userId, String type){

        // 根据微信订单号查询是否存在该订单
        logger.info("===> 根据微信订单号查询是否存在该订单");
        Date date = new Date();
        Integer orderStatus = SUCCESS.equalsIgnoreCase(type)?PayStatusEnum.SUCCESS.getValue():PayStatusEnum.FAIL.getValue();

        // 查询退款记录
        RefundLog refundLog = refundLogMapper.getBySn(refundIn.getOrderSn());
        // 根据订单号查询订单
        Order order = orderMapper.getBySn(refundIn.getOrderSn());
        // 查询用户信息
        User user = userMapper.get(userId);

        logger.info("===> 查看退款记录是否存在");
        // 存在记录且为失败
        if(refundLog != null){
            // 存在且状态为成功，则退出
            if(refundLog.getRefundStatus() == 1){
                logger.info("===> 订单已经完成");
                return;
            }
            if(FAIL.equalsIgnoreCase(type)){
                logger.info("===> 订单失败");
                return;
            }
            // 当状态为成功时，更新退款记录为成功
            refundLog.setPayTime(DateUtils.parseSqlDate(weiRefundResult.getPaymentTime()));
            refundLog.setRefundStatus(1);
            refundLogMapper.update(refundLog);

        }
        logger.info("===> 不存在记录,则保存记录");
        // 不存在记录,则保存记录
        if(refundLog == null){

            String paymentSn = "";
            Integer status = 2;
            Date payTime = date;

            if(SUCCESS.equalsIgnoreCase(type)){
                paymentSn = weiRefundResult.getPaymentNo();
                status = 1;
                payTime = DateUtils.parseSqlDate(weiRefundResult.getPaymentTime());

            }

            refundLog = new RefundLog();
            refundLog.setOrderId(order.getId());
            refundLog.setOrderSn(order.getOrderSn());
            refundLog.setPaymentSn(paymentSn);
            refundLog.setUserId(userId);
            refundLog.setTotalPrice(order.getTotalPrice());
            refundLog.setRefundStatus(status);
            refundLog.setPayTime(payTime);
            refundLog.setCreateTime(date);
            refundLogMapper.insert(refundLog);
        }

        //  1.更新订单状态
        logger.info("===> 更新订单状态");
        order.setPayStatus(orderStatus);
        orderMapper.update(order);

        // 如果状态为失败则不保存流水也不更改用户账户
        if(orderStatus == PayStatusEnum.FAIL.getValue()){
            return;
        }

        // 2.当前状态返回成功则 新增新活流水记录
        logger.info("===> 保存用户流水资金");
        // 保存用户流水资金
        BigDecimal defaultValue = new BigDecimal(0);
        BigDecimal nowBalance = user.getBalance().subtract(refundLog.getTotalPrice());// 计算用户新余额
        NewLifeSpendLog newLifeSpendLog = new NewLifeSpendLog();
        newLifeSpendLog.setMoneySn(refundLog.getOrderSn()); // 设置流水号
        newLifeSpendLog.setOrderId(refundLog.getOrderId()); // 订单id
        newLifeSpendLog.setOrderSn(refundLog.getOrderSn()); // 订单号
        newLifeSpendLog.setType(NewLifeSpendLogTypeEnum.BALANCE_REFUND.getValue()); // 充值类型
        newLifeSpendLog.setTypeRemark(NewLifeSpendLogTypeRemarkEnum.REFUND.getValue()); // 记录类型
        newLifeSpendLog.setAmount(refundLog.getTotalPrice()); // 操作金额
        newLifeSpendLog.setCouponType(0);
        newLifeSpendLog.setCouponAmount(defaultValue);
        newLifeSpendLog.setUserId(userId);
        newLifeSpendLog.setUserName(user.getUserName());
        newLifeSpendLog.setUserMobile(user.getMobile());
        newLifeSpendLog.setUserAvatarUrl(user.getAvatarUrl());
        newLifeSpendLog.setUserOldBalance(user.getBalance()); // 用户充值前余额
        newLifeSpendLog.setUserGainAmount(refundLog.getTotalPrice()); // 用户实得金额
        newLifeSpendLog.setUserType(UserTypeEnum.CAR.getValue()); // 用户类型
        newLifeSpendLog.setUserNowBalance(nowBalance); // 用户当前余额
        // 桩站和新活信息，设置为默认值
        newLifeSpendLog.setStationId(0);
        newLifeSpendLog.setStationName("");
        newLifeSpendLog.setCommissionRation(defaultValue);
        newLifeSpendLog.setStationGainAmountCharge(defaultValue);
        newLifeSpendLog.setStationGainAmountService(defaultValue);
        newLifeSpendLog.setServicePrice(defaultValue);
        newLifeSpendLog.setStationGainAmount(defaultValue);
        newLifeSpendLog.setStationOldNoCheckAmount(defaultValue);
        newLifeSpendLog.setStationNowNoCheckAmount(defaultValue);
        newLifeSpendLog.setStationOldBalance(defaultValue);
        newLifeSpendLog.setStationNowBalance(defaultValue);
        newLifeSpendLog.setNewLifeGainAmount(defaultValue);
        newLifeSpendLog.setRemark("用户退款");
        newLifeSpendLog.setCreateTime(date);

        newLifeSpendLogMapper.insert(newLifeSpendLog);

        // 计算用户余额,并保存
        BigDecimal balance = user.getBalance().subtract(refundLog.getTotalPrice());
        user.setBalance(balance);
        userMapper.update(user);

        UserAccount userAccount = userAccountMapper.getByUserId(userId);
        BigDecimal chargePrice = userAccount.getChargeBalance().subtract(refundLog.getTotalPrice());
        userAccount.setChargeBalance(chargePrice);
        userAccountMapper.update(userAccount);
    }


    @Override
    public UserAccountOut getAccount(Integer userId) {
        UserAccount account = this.accountMapper.getByUserId(userId);
        if(account == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        UserAccountOut out = dozer.map(account, UserAccountOut.class);
        BigDecimal price = account.getChargeBalance().add(account.getGiveBalance().add(account.getCouponChargeBalance()));
        out.setBalance(price);
        return out;
    }


    @Override
    public FormulaModeGeneralOut formulaMode(Integer userId) {

        FormulaModeGeneralOut out = new FormulaModeGeneralOut();

        List<FormulaModeOut> list = Lists.newArrayList();

        // 查询用户可退款余额
        UserAccount account = this.accountMapper.getByUserId(userId);
        BigDecimal giveBalance = account.getGiveBalance();
        BigDecimal couponChargeBalance = account.getCouponChargeBalance();
        BigDecimal balance = account.getChargeBalance().add(giveBalance.add(couponChargeBalance));
        out.setBalance(balance);
        out.setChargePrice(account.getChargeBalance());
        out.setGivePrice(giveBalance.add(couponChargeBalance));

        account.setChargeBalance(giveBalance.add(couponChargeBalance));

        // 查询用户优惠充值流水
        List<NewLifeSpendLog> newLifeSpendLog = newLifeSpendLogMapper.getChargeListByUser(userId,
                RechargeTypeEnum.STATION_ADMIN.getValue(),
                NewLifeSpendLogTypeEnum.USER_RECHARGE.getValue(),
                NewLifeSpendLogTypeRemarkEnum.RECHARGE.getValue());
        newLifeSpendLog.stream().forEach(item ->{
            FormulaModeOut formulaModeOut = new FormulaModeOut();
            // 如果小于优惠充值余额
            if(account.getChargeBalance().compareTo(new BigDecimal(0)) > 0 &&
                    item.getAmount().compareTo(account.getChargeBalance()) == -1){

                account.setChargeBalance(account.getChargeBalance().subtract(item.getAmount()));

                formulaModeOut.setPrice(item.getAmount());
                formulaModeOut.setGivePrice(item.getAmount());
                formulaModeOut.setChargeTime(item.getCreateTime());
                formulaModeOut.setChargeTimestamp(item.getCreateTime());
                list.add(formulaModeOut);

            }else if(account.getChargeBalance().compareTo(new BigDecimal(0)) > 0 &&
                    item.getAmount().compareTo(account.getChargeBalance()) >= 0){

                formulaModeOut.setGivePrice(account.getChargeBalance());
                formulaModeOut.setPrice(item.getAmount());
                formulaModeOut.setChargeTime(item.getCreateTime());
                formulaModeOut.setChargeTimestamp(item.getCreateTime());
                list.add(formulaModeOut);
                return;
            }else {
                return;
            }
        });
        out.setFormulaModeOut(list);
        return out;
    }

    /**
     * @param mchId 商户号
     * @param url 请求地址
     * @param data 参数（xml）
     * @return
     * @throws Exception
     */
    private String doRefund(String mchId,String url, String data) throws Exception {

        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //P12文件目录 证书路径，这里需要你自己修改，linux下还是windows下的根路径
//        String filepath = "E:/document/certificate/";
//        FileInputStream instream = new FileInputStream(filepath + "apiclient_cert.p12");
        InputStream input  = getClass().getClassLoader().getResourceAsStream("certificate/apiclient_cert.p12");
        try {
            keyStore.load(input, mchId.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            input.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
