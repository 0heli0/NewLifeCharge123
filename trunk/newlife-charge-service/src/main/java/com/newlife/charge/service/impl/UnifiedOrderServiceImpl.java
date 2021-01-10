package com.newlife.charge.service.impl;


import com.newlife.charge.common.CommonUtil;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.HttpUtil;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.common.TelCharg.TelChargApi;
import com.newlife.charge.common.weixin.WeiXinUtils;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.exModel.WeiChatOrderDetailEx;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.MaxUseableCouponOut;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.UnifiedOrderService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 车主端 微信支付统一下单service类
 *
 */
@Service
@Transactional
public class UnifiedOrderServiceImpl implements UnifiedOrderService {


    @Value("${newLife.charge.ip}")
    private String ip;

    @Value("${pay.isReal}")
    private boolean isReal;

    private final static String SUCCESS = "SUCCESS";
    private final static String FAIL = "FAIL";

    private Logger logger = LoggerFactory.getLogger(UnifiedOrderServiceImpl.class);

    @Autowired
    private RechargeLogMapper rechargeLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PreChargeMapper preChargeMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private WeixinPayLogMapper weixinPayLogMapper;

    @Autowired
    private NewLifeSpendLogMapper newLifeSpendLogMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public UnifiedOrderResult unifiedOrder(UnifiedOrderQueryIn queryIn, Integer userId) {

        UnifiedOrderParams paramsInfo = new UnifiedOrderParams();
        Date date = new Date();
        // 查询订单
        WeiChatOrderDetailEx orderDetail = orderMapper.getWeiChatOrderDetail(queryIn.getOrderSn());

        if(orderDetail == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        // 最终支付金额
        BigDecimal price = orderDetail.getTotalPrice();
        // 优惠券额度
        BigDecimal couponPrice = orderDetail.getCouponPrice();
        String attach = "全额充值";
        if(couponPrice != null){
            attach = "使用优惠券充值";
        }

        //计算充值金额，精确到分
        int finalPrice = price.multiply(new BigDecimal(100)).intValue();
        if(!isReal){
            logger.info("===> 模拟环境");
            finalPrice = 100;
        }

        String nonceStr = StringUtil.getUUId();

        paramsInfo.setAppId(WeiXinUtils.APP_Id);
        paramsInfo.setBody("新活充电");
        paramsInfo.setNonceStr(nonceStr);
        paramsInfo.setMchId(WeiXinUtils.MCH_Id);
        paramsInfo.setAttach(attach);
        paramsInfo.setSpbillCreateIp(ip);
        paramsInfo.setNotifyUrl("http://182.148.12.26:8990/api/appletWeiChatPaycar/weiChatPayCallBack");
        paramsInfo.setOpenid(queryIn.getOpenId());
        paramsInfo.setOutTradeNo(orderDetail.getOrderSn());
        // 测试阶段默认扣1分钱
        paramsInfo.setTotalFee(finalPrice);
//        paramsInfo.setTotalFee(finalPrice);
        paramsInfo.setTradeType("JSAPI");
//        paramsInfo.setSceneInfo("{\"store_info\" : {\"id\": \"SZTX001\",\"name\": \"腾大餐厅\",\"area_code\": \"440305\",\"address\": \"科技园中一路腾讯大厦\" }}");
//        paramsInfo.setSceneInfo("软件园A");
//        paramsInfo.setSceneInfo("park");


        String str = WeiXinUtils.createSign(UnifiedOrderParams.class,paramsInfo);

        // 生成签名
        String stringSignTemp=str+"&key="+ WeiXinUtils.APP_KEY;
        // md5加密
        logger.info("===> stringSignTemp:"+str);
        logger.info("===> stringSignTemp:"+stringSignTemp);
        String md5 = new Md5Hash(stringSignTemp).toString().toUpperCase();
        paramsInfo.setSign(md5);

        // 生成xml
        String params = CommonUtil.toXml(UnifiedOrderParams.class, paramsInfo);

        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        logger.info("===> params:\n"+params);

        String result = HttpUtil.sendPost(url,params);

        logger.info("===> result: "+result);

        // 解析返回数据
        UnifiedOrder orderResult = (UnifiedOrder)CommonUtil.toBean(UnifiedOrder.class, result);
        logger.info("===> orderResult:"+orderResult);

        if(orderResult ==null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        if(FAIL.equalsIgnoreCase(orderResult.getReturnCode())){
            throw new BizException(ERROR.DATA_NOT_FOUND.code(), orderResult.getReturnMsg());

        }

        if(FAIL.equalsIgnoreCase(orderResult.getResultCode())){
            throw new BizException(ERROR.PARAMATER_ERROR.code(), orderResult.getErrCodeDes());
        }

        UnifiedOrderResult unifiedOrderResult = dozer.map(orderResult, UnifiedOrderResult.class);


        String timeStamp = DateUtils.getSecondTimestamp(new Date()) +"";
        unifiedOrderResult.setNonceStr(StringUtil.getUUId());
        unifiedOrderResult.setTimestamp(timeStamp);
        unifiedOrderResult.setOrderSn(orderDetail.getOrderSn());
        unifiedOrderResult.setPrepayId("prepay_id="+unifiedOrderResult.getPrepayId());

        // 生成签名
        String signType = String.format("appId=%s&nonceStr=%s&package=%s&signType=MD5&timeStamp=%s&key=%s",
                WeiXinUtils.APP_Id,unifiedOrderResult.getNonceStr(),unifiedOrderResult.getPrepayId(),timeStamp,WeiXinUtils.APP_KEY);
        String signTypeMd5 = new Md5Hash(signType).toString().toUpperCase();

        logger.info("===> signType:"+signType+"\n"+signTypeMd5);

        unifiedOrderResult.setSignType(signTypeMd5);
        unifiedOrderResult.setSign(signTypeMd5);

        logger.info("===> unifiedOrderResult:"+unifiedOrderResult);


        return unifiedOrderResult;
    }

    @Override
    public String createOrder(CreateOrderIn queryIn, Integer userId, String type) {
        Lock lock = new ReentrantLock(false);

        lock.lock();
        try {
            Date date = new Date();
            // 生成新的订单号
            String orderSn = TelChargApi.getNewOrderSn(type);
            // 计算充值金额
            PreCharge preCharge = preChargeMapper.get(queryIn.getPreId());
            // 最终支付金额
            BigDecimal price = preCharge.getFinalPrice();
            // 优惠券额度
            BigDecimal couponPrice = new BigDecimal(0);
            if(queryIn.getCouId() !=null && queryIn.getCuId() != null){
                Coupon coupon = couponMapper.get(queryIn.getCouId());
                UserCoupon userCoupon = userCouponMapper.get(queryIn.getCuId());
                // 优惠券满足使用条件时重新计算用户支付金额，并将用户优惠券状态改为冻结
                if(userCoupon.getStatus().equals(UserCouponStatusEnum.UNUSED.getValue()) && // 使用状态必须是未使用
                        coupon.getStatus().equals(CouponStatusEnum.USED.getValue()) && // 优惠券没有下架
                        coupon.getUseEndTime().getTime() >= date.getTime() && // 优惠券没有过期
                        coupon.getType().equals(CouponTypeEnum.Charge_Type.getValue()) && // 优惠券是充值优惠券
                        coupon.getThresholdPrice().intValue() <= price.intValue()){ // 充值金额不小于门槛

                    logger.info(("===> 进入优惠券充值"));

                    couponPrice = coupon.getPrice();

                    UserCoupon userCouponUpdate = new UserCoupon();
                    userCouponUpdate.setId(queryIn.getCuId());
                    userCouponUpdate.setOrderSn(orderSn);
                    userCouponUpdate.setStatus(UserCouponStatusEnum.LOCKED.getValue());
                    userCouponUpdate.setFrozenTime(date);
                    userCouponMapper.update(userCouponUpdate);
                }

            }else {
                queryIn.setCouId(0);
                queryIn.setCuId(0);
            }

            // 用户需要支付的金额
            BigDecimal chargePrice = preCharge.getOriginalPrice().subtract(couponPrice);

            // 当返回成功后，保存订单
            Order order = new Order();
            order.setOrderSn(orderSn);
            order.setUserId(userId);
            order.setTotalPrice(chargePrice);
            order.setSumPrice(preCharge.getOriginalPrice());
            order.setCouponPrice(couponPrice);
            order.setOrderType(OrderTypeEnum.RECHARGE.getValue());
            order.setPayType(PayTypeEnum.WEIXIN.getValue());
            order.setPayStatus(PayStatusEnum.NONE.getValue());
            order.setPayTime(date);
            order.setCreateTime(date);
            orderMapper.insert(order);

            // 保存充值记录
            RechargeLog rechargeLog = new RechargeLog();
            rechargeLog.setOrderId(order.getId());
            rechargeLog.setOrderSn(order.getOrderSn());
            rechargeLog.setUserId(userId);
            rechargeLog.setPreChargeId(preCharge.getId());
            rechargeLog.setOriginalPrice(preCharge.getOriginalPrice());
            rechargeLog.setFinalPrice(preCharge.getFinalPrice());
            rechargeLog.setUserCouponId(queryIn.getCouId());
            rechargeLog.setCouponPrice(couponPrice);
            rechargeLog.setTotalPrice(chargePrice);
            rechargeLog.setPayTime(date);
            rechargeLog.setCreateTime(date);
            rechargeLogMapper.insert(rechargeLog);

            return orderSn;
        }finally {
            lock.unlock();
            logger.info("=== > 释放锁");
        }
    }

    @Override
    public void weiChatPayResult(StringIn orderSnIn, Integer userId) {


        CheckOrderParams checkOrderParams = new CheckOrderParams();
        checkOrderParams.setAppId(WeiXinUtils.APP_Id);
        checkOrderParams.setMchId(WeiXinUtils.MCH_Id);
        checkOrderParams.setOutTradeNo(orderSnIn.getStr());
        checkOrderParams.setNonceStr(StringUtil.getUUId());
        checkOrderParams.setSignType("MD5");

        String str = WeiXinUtils.createSign(CheckOrderParams.class,checkOrderParams);

        String stringSignTemp=str+"&key="+WeiXinUtils.APP_KEY; //注：key为商户平台设置的密钥key
        String md5 = new Md5Hash(stringSignTemp).toString().toUpperCase();
        checkOrderParams.setSign(md5);

        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        // 生成xml
        String params = CommonUtil.toXml(CheckOrderParams.class, checkOrderParams);

        logger.info("===> 查询订单请求参数："+params);

        String result = HttpUtil.sendPost(url,params);

        logger.info("===> 查询订单请求结果："+result);

        WeiChatCheckOrderResult orderResult = (WeiChatCheckOrderResult)CommonUtil.toBean(WeiChatCheckOrderResult.class, result);

        if(FAIL.equalsIgnoreCase(orderResult.getReturnCode())){
            throw new BizException(ERROR.DATA_NOT_FOUND.code(),orderResult.getReturnMsg());
        }

        weiChatPaySuccess(orderResult, userId);

    }

    @Override
    public void weiChatPayCallBack(WeiChatCheckOrderResult orderResult) {

        logger.info("===> 微信支付回调参数接收成功："+orderResult);

        if(FAIL.equalsIgnoreCase(orderResult.getReturnCode())){
            throw new BizException(ERROR.DATA_NOT_FOUND.code(),orderResult.getReturnMsg());
        }
        // 查询userId
        Order order = orderMapper.getBySn(orderResult.getOutTradeNo());

        weiChatPaySuccess(orderResult, order.getUserId());
    }

    /**
     * 查询微信支付订单后，对各个充值表进行操作
     * @param orderResult
     * @param userId
     */
    private void weiChatPaySuccess(WeiChatCheckOrderResult orderResult, Integer userId) {
        Lock lock = new ReentrantLock(false);
        lock.lock();
        try {
            Date date = new Date();
            // 当微信充值成功后操作
            if (SUCCESS.equalsIgnoreCase(orderResult.getReturnCode()) &&
                    SUCCESS.equalsIgnoreCase(orderResult.getResultCode()) &&
                    SUCCESS.equalsIgnoreCase(orderResult.getTradeState())) {

                // 根据微信订单号查询是否存在该订单
                logger.info("===> 根据微信订单号查询是否存在该订单");
                WeixinPayLog log = weixinPayLogMapper.getBySn(orderResult.getTransactionId());
                if(log != null){
                    logger.info("===> 订单已经完成");
                    return;
                }

                logger.info("===> 根据订单号查询");
                // 根据订单号查询订单
                Order order = orderMapper.getBySn(orderResult.getOutTradeNo());
                // 根据订单号查询充值记录
                RechargeLog rechargeLog = rechargeLogMapper.getBySn(orderResult.getOutTradeNo());
                // 查询用户信息
                User user = userMapper.get(userId);
                // 查询用户资金表
                UserAccount userAccount = userAccountMapper.getByUserId(userId);

                logger.info("===> 保存微信充值记录");
                // 保存微信充值记录
                WeixinPayLog weixinPayLog = dozer.map(orderResult, WeixinPayLog.class);
                weixinPayLog.setCreateTime(date);
                weixinPayLogMapper.insertByObj(weixinPayLog);

                logger.info("===> 保存用户流水资金");
                // 保存用户流水资金
                BigDecimal defaultValue = new BigDecimal(0);
                BigDecimal nowBalance = user.getBalance().add(rechargeLog.getOriginalPrice());// 计算用户新余额
                NewLifeSpendLog newLifeSpendLog = new NewLifeSpendLog();
                newLifeSpendLog.setMoneySn(order.getOrderSn()); // 设置流水号
                newLifeSpendLog.setOrderId(order.getId()); // 订单id
                newLifeSpendLog.setOrderSn(order.getOrderSn()); // 订单号
                newLifeSpendLog.setType(NewLifeSpendLogTypeEnum.USER_RECHARGE.getValue()); // 充值类型
                newLifeSpendLog.setTypeRemark(NewLifeSpendLogTypeRemarkEnum.RECHARGE.getValue()); // 记录类型
                newLifeSpendLog.setAmount(rechargeLog.getOriginalPrice()); // 操作金额
                newLifeSpendLog.setCouponType(0);// 优惠券类型
                newLifeSpendLog.setCouponAmount(defaultValue);// 优惠券金额
                newLifeSpendLog.setUserId(userId);
                newLifeSpendLog.setUserName(user.getUserName());
                newLifeSpendLog.setUserMobile(user.getMobile());
                newLifeSpendLog.setUserAvatarUrl(user.getAvatarUrl());
                newLifeSpendLog.setUserOldBalance(user.getBalance()); // 用户充值前余额
                newLifeSpendLog.setUserGainAmount(rechargeLog.getOriginalPrice()); // 用户实得金额
                newLifeSpendLog.setUserType(UserTypeEnum.CAR.getValue()); // 用户类型
                newLifeSpendLog.setUserNowBalance(nowBalance); // 用户当前余额
                // 桩站和新活信息，设置为默认值
                newLifeSpendLog.setStationId(0);
                newLifeSpendLog.setStationName("");
                newLifeSpendLog.setCommissionRation(defaultValue);
                newLifeSpendLog.setServicePrice(defaultValue);
                newLifeSpendLog.setStationGainAmountCharge(defaultValue);
                newLifeSpendLog.setStationGainAmountService(defaultValue);
                newLifeSpendLog.setStationGainAmount(defaultValue);
                newLifeSpendLog.setStationOldNoCheckAmount(defaultValue);
                newLifeSpendLog.setStationNowNoCheckAmount(defaultValue);
                newLifeSpendLog.setStationOldBalance(defaultValue);
                newLifeSpendLog.setStationNowBalance(defaultValue);
                newLifeSpendLog.setNewLifeGainAmount(defaultValue);
                newLifeSpendLog.setRemark("微信充值");
                newLifeSpendLog.setCreateTime(date);

                newLifeSpendLogMapper.insert(newLifeSpendLog);

                logger.info("===> 更新订单状态");
                // 更新订单状态
                order.setPayStatus(PayStatusEnum.SUCCESS.getValue());
                order.setPayTime(date);
                orderMapper.update(order);

                // 更新支付记录，支付时间
                rechargeLog.setPayTime(new Date());
                rechargeLogMapper.update(rechargeLog);

                // 计算充值金额
                BigDecimal originalPrice = rechargeLog.getOriginalPrice();
                // 计算优惠金额
                logger.info("===> rechargeLog:"+rechargeLog.toString());
                BigDecimal couponPrice = rechargeLog.getCouponPrice();
                if(couponPrice.intValue() > 0){
                    BigDecimal newCouponPrice = couponPrice.add(userAccount.getGiveBalance()); // 当前优惠+以前优惠 = 现在的赠送金额
                    BigDecimal newOriginalPrice = originalPrice.add(userAccount.getCouponChargeBalance()).subtract(couponPrice); // 充值总金额+以前优惠充值金额-优惠金额
                    userAccount.setCouponChargeBalance(newOriginalPrice);
                    userAccount.setGiveBalance(newCouponPrice);
                    // 更新用户优惠券状态
                    UserCoupon userCoupon = userCouponMapper.getByOrederSn(order.getOrderSn(), UserCouponStatusEnum.LOCKED.getValue());
                    if(userCoupon != null){
                        userCoupon.setStatus(UserCouponStatusEnum.USED.getValue());
                        userCouponMapper.update(userCoupon);
                    }

                }else {
                    originalPrice = originalPrice.add(userAccount.getChargeBalance());
                    userAccount.setChargeBalance(originalPrice);
                }
                logger.info("===> 更改用户资金account表");
                // 更改用户资金account表
                userAccountMapper.update(userAccount);

                logger.info("===> 更改user表用户余额");
                // 更改user表用户余额
                BigDecimal oldUserBalance = user.getBalance();
                BigDecimal giveBalance = userAccount.getGiveBalance();
                BigDecimal couponChargeBalance = userAccount.getCouponChargeBalance();
                BigDecimal chargeBalance = userAccount.getChargeBalance();
                BigDecimal nowUserBalance = chargeBalance.add(giveBalance.add(couponChargeBalance));
                user.setBalance(nowUserBalance);
                // 当用户原余额加本次可得金额时，更新用户余额，否则抛出异常
                logger.info(
                        String.format("===> 用户余额：%s，用户账户计算后余额：%s",
                                oldUserBalance.add(rechargeLog.getOriginalPrice()),
                                nowUserBalance)
                );

                if(oldUserBalance.add(rechargeLog.getOriginalPrice()).compareTo(nowUserBalance) ==0){
                    userMapper.update(user);
                }else {
                    logger.info("===> 用户余额结算错误");
                    throw new BizException(ERROR.USER_BALANCE_ERROR);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException(ERROR.INTERNAL_ERROR.code(),e.getMessage());
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public MaxUseableCouponOut getMaxUsableCoupon(PriceIn priceIn, Integer userId) {

        MaxUseableCouponOut out = this.userCouponMapper.getMaxUsableCoupon(priceIn.getPrice(), userId);

        return out;
    }

    @Override
    public void cancelOrder(CancelOrderIn cancelOrderIn, Integer userId) {
        // 将订单状态设置为取消
        Order order = orderMapper.getBySn(cancelOrderIn.getOrderSn());
        order.setPayStatus(PayStatusEnum.FAIL.getValue());
        orderMapper.update(order);

        // 将优惠券状态设置为可以使用
        if(cancelOrderIn.getUserCouponId() != null && cancelOrderIn.getUserCouponId() !=0){
            UserCoupon userCoupon = userCouponMapper.get(cancelOrderIn.getUserCouponId());
            userCoupon.setStatus(UserCouponStatusEnum.UNUSED.getValue());
            userCoupon.setOrderSn("");
            userCouponMapper.update(userCoupon);
        }
    }
}
