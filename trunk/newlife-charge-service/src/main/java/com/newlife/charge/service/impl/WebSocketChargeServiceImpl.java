package com.newlife.charge.service.impl;

import com.google.common.collect.Lists;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.domain.exModel.TruckSpaceEx;
import com.newlife.charge.core.dto.in.PileHistoryDataIn;
import com.newlife.charge.core.dto.in.PileRealTimeDataIn;
import com.newlife.charge.core.dto.in.StageDataIn;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.WebSocketChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Transactional
public class WebSocketChargeServiceImpl implements WebSocketChargeService {


    static Logger log= LoggerFactory.getLogger(WebSocketServerImpl.class);

    // 绑定枪和用户id
    private static final String Bind_Gun_User_Prefix = "gun_user_id_";

    // 用户绑定枪
    private static final String Key_Prefix_User = "user_parkingLot_";

    // 绑定充电枪前缀
    private static final String Key_Prefix = "parkingLot_";

    // 绑定充电枪状态前缀
    private static final String Gun_Status_Prefix = "parkingLot_gun_status_";

    // 将枪和订单号绑定
    private static final String Order_Sn_Prefix = "orderSnGun_";

    @Autowired
    private ChargeLogMapper chargeLogMapper;

    @Autowired
    private StationClientGunMapper gunMapper;

    @Autowired
    private StationParkingLotMapper stationParkingLotMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private NewLifeSpendLogMapper newLifeSpendLogMapper;

    @Autowired
    private ClientStationMapper clientStationMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StationClientGunMapper stationClientGunMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private StationInfoMapper stationInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 实时数据
     * @param id
     * @param status 充电枪状态 1."离线",2"空闲中",3"连接中",4"充电中",5"被预约",6"排队中"
     * @param pileRealTimeDataIn 充电枪实时数据
     */
    @Override
    public ChargeBeginOut sendRealTimeMessage(Integer id, Integer status, PileRealTimeDataIn pileRealTimeDataIn) {
        // 获取userId
        String key = Bind_Gun_User_Prefix+id;
        if(!RedisUtils.isExistRedis(key)){
            log.info("===> 该充电枪没有绑定用户，未进行充电操作");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        String userId = RedisUtils.getRedisValue(key);

        Long chargeTime = 0L;
        if(StringUtil.isNotEmpty(pileRealTimeDataIn.getChargeTime())){
            chargeTime = Long.parseLong(pileRealTimeDataIn.getChargeTime());
        }

        // 根据枪id和用户id查找订单信息
        ChargeLog chargeLog = chargeLogMapper.getInfoByParams(Integer.parseInt(userId),
                id,
                ChargeLogStatusEnum.CHARGE_ING.getValue());

        // 根据充电枪id查询车位详情信息
        StationGunAddressDetail addressDetail = gunMapper.getStationGunAddressDetail(id);
        String address = String.format("%s，%s号充电桩的%s号充电枪",
                addressDetail.getAddress(), addressDetail.getClientCode(), addressDetail.getGunCode());
        log.info("===>address:"+address);

        ChargeBeginOut out = new ChargeBeginOut();
        out.setClientGunId(id);
        out.setOrderSn(chargeLog.getOrderSn()); // 订单号
        out.setAddress(addressDetail.getAddress()); // 桩站详情地址
        out.setLotCode(addressDetail.getLotCode());
        out.setChargeAmount(pileRealTimeDataIn.getChargeAmount()); // 充电金额（元）
        out.setChargeEnergy(pileRealTimeDataIn.getChargeEnergy());// 充电电能(KWH)
        out.setChargeTime(DateUtils.secondToTime(chargeTime));// 充电时间
        out.setGunCode(chargeLog.getClientGunCode());// 枪编号
        out.setPercentage(pileRealTimeDataIn.getCurrentSoc());// 汽车电量百分比(%),百分号左边的整数
        out.setStatus(status);// 充电枪状态充电状态(1：离线,2:空闲中(暂定此状态包含完成状态),3:连接中,4:充电中,5:被预约,6:排队中)

        // 绑定枪状态到redis
        String gunKey = getKey(Gun_Status_Prefix, id);
        RedisUtils.setRedisValue(gunKey,status+"",24, TimeUnit.HOURS);
        return out;
    }

    /**
     * 插枪
     * @param id     充电枪ID
     * @param status 充电枪状态 1."离线",2"空闲中",3"连接中",4"充电中",5"被预约",6"排队中"
     * @param pileRealTimeDataIn 充电枪实时数据
     */
    @Override
    public PlugInGunOut plugInGun(Integer id, Integer status, PileRealTimeDataIn pileRealTimeDataIn) {
        // 获取userId
        String key = Bind_Gun_User_Prefix+id;
        log.info("===> key:"+key);
        if(!RedisUtils.isExistRedis(key)){
            log.info("===> 该充电枪没有绑定用户，未进行充电操作");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        String userId = RedisUtils.getRedisValue(key);

        // 绑定枪状态到redis
        String gunKey = getKey(Gun_Status_Prefix, id);
        RedisUtils.setRedisValue(gunKey,status+"",24, TimeUnit.HOURS);
        // 根据充电枪id查询车位详情信息
        StationGunAddressDetail addressDetail = gunMapper.getStationGunAddressDetail(id);
        String address = String.format("%s，%s号充电桩的%s号充电枪",
                addressDetail.getAddress(), addressDetail.getClientCode(), addressDetail.getGunCode());
        log.info("===>address:"+address);
        // 根据车位id取消车位
        if(addressDetail.getLotId() != null){
            log.info("===> 插枪后取消车位绑定状态");
            StationParkingLot lot = new StationParkingLot();
            lot.setId(addressDetail.getLotId());
            lot.setStatus(StationParkingLotStatusEnum.FREE.getValue());
            stationParkingLotMapper.update(lot);
        }



        // 将数据返回给客户端
        PlugInGunOut out = new PlugInGunOut();
        out.setClientGunId(id);
        out.setInsert(true);
        out.setStatus(1);
        out.setAddress(addressDetail.getAddress());
        out.setLotCode(addressDetail.getLotCode());
        return out;
    }

    /**
     * 充电成功
     * @param infoIn
     */
    @Override
    public ChargeEndOut chargeStopSuccess(PileHistoryDataIn infoIn){

        log.info("===> PileHistoryDataIn:"+infoIn.toString());
        log.info("===> 进入充电结束结算操作");

        // 先通过用户手机号查询用户充电订单信息
        log.info("===> 先通过用户手机号查询用户充电订单信息");
        ChargeLog chargeLogInfo = chargeLogMapper.getByMobile(infoIn.getChargeCardNo());
        if(chargeLogInfo == null){
            log.info("===> 没有充值记录");
            return null;
        }

        // 查询充电枪信息
        log.info("===> 查询充电枪信息");
        StationClientGunEx gunEx = stationClientGunMapper.getInfoByParams(
                chargeLogInfo.getClientGunId(),
                null,
                null,
                null,
                null,
                null);

        // 查询订单编号
        log.info("===> 查询订单编号");
        // TODO：取消订单缓存
        /*String key_order = getKeyByGun(Order_Sn_Prefix, gunEx.getId());
        if(!RedisUtils.isExistRedis(key_order)){
            return null;
        }*/

//        String orderSn = RedisUtils.getRedisValue(key_order);

        // 或者通过交易单号--订单id查询order
        Integer orderId = infoIn.getRecordNo();
//                Order order = orderMapper.get(orderId);

        // 查询订单
        log.info("===> 查询订单");
//        Order order = orderMapper.getBySn(orderSn);
        Order order = orderMapper.get(orderId);
        Integer gunId = gunEx.getId();
        Integer stationId = gunEx.getStationId();
        Integer userId = order.getUserId();


        Date date = new Date();

        // 查询用户信息
        log.info("===> 查询用户信息");
        User user = userMapper.get(userId);
        UserAccount userAccount = userAccountMapper.getByUserId(userId);
        if(user == null || userAccount == null){
            log.info("===> 用户信息出错，没有用户数据");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        // 修改充电记录
        log.info("===> 修改充电记录");
        ChargeLog chargeLog = chargeLogMapper.getByOrderSn(order.getOrderSn());

        if(chargeLog == null){
            log.info("===> 没有电记录");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        // 充电结束状态与原因
        if(infoIn.getChargeEndReason() == 1){
            chargeLog.setStopType(ChargeStopTypeEnum.ACCIDENT_STOP.getValue());
        }else if (chargeLog.getStopType() == ChargeLogStatusEnum.CHARGE_ING.getValue()){
            chargeLog.setStopType(ChargeStopTypeEnum.COMPLETE_STOP.getValue());
        }
        chargeLog.setStatus(ChargeLogStatusEnum.CHARGE_SUCCESS.getValue());

        // 查找该用户可用的充电优惠券-（使用顺序：1.截止时间越近的越先被使用；2.优惠力度越大的越先被使用；3.领取时间越早的越先被使用。）
        log.info("===> 查找该用户可用的充电优惠券");
        BigDecimal chargeAmount = new BigDecimal(infoIn.getChargeAmount());
        UserUseCoupon userUseCoupon = userCouponMapper.getFirstToUseCoupon(stationId, chargeAmount, userId);
        UserCoupon userCoupon = new UserCoupon();

        // 取出充电时段电费
        List<StageDataIn> stageDataInList = infoIn.getStageDataInList();
        List<BigDecimal> priceList = Lists.newArrayList();
        if(stageDataInList != null && stageDataInList.size()>0){
            stageDataInList = stageDataInList.stream().filter(item -> Long.parseLong(item.getPower()) > 0).collect(Collectors.toList());
            // 计算时段电费单价
            stageDataInList.forEach(item ->{
                BigDecimal price = new BigDecimal(item.getBalance());
                BigDecimal power = new BigDecimal(item.getPower());
                BigDecimal singlePrice = price.divide(power);
                priceList.add(singlePrice.setScale(2));
            });
        }
        // 排序
        Collections.sort(priceList);


        // 计算实际使用金额
        log.info("===> 计算实际使用金额");
        // 先计算订单金额
        order.setTotalPrice(new BigDecimal(infoIn.getChargeAmount()));// 订单金额，总金额-优惠券金额
        if(userUseCoupon != null){
            order.setTotalPrice(order.getTotalPrice().subtract(userUseCoupon.getPrice()));
            // 将优惠券设置为冻结状态 --暂时不用
            /*userCoupon.setStatus(2);
            userCoupon.setId(userUseCoupon.getId());
            userCouponMapper.update(userCoupon);*/
            chargeLog.setPriceCoupon(userUseCoupon.getPrice());
            chargeLog.setUserCouponId(userCoupon.getCouponId());
        }

        if(StringUtil.isEmpty(infoIn.getChargeAmount())){
            infoIn.setChargeAmount("0");
        }
        log.info("===> 修改充电记录");
        // TODO: 补充充电记录 --补充完毕，待测试
        chargeLog.setChargePrice(new BigDecimal(infoIn.getChargeAmount()));
        chargeLog.setPriceReal(order.getTotalPrice());
        chargeLog.setChargePrices(priceList.get(0)+"~"+priceList.get(priceList.size()-1));
        chargeLog.setDegreeReal(new BigDecimal(infoIn.getChargeEnergy()));
        chargeLogMapper.update(chargeLog);

        // 修改订单信息
        log.info("===> 修改订单信息");
        order.setPayType(PayTypeEnum.ACCOUNT_BALANCE.getValue());
        order.setPayStatus(PayStatusEnum.SUCCESS.getValue());
        order.setPayTime(new Date());
        orderMapper.update(order);

        // TODO: 补充充电消费桩站和新活相关信息 --补充完毕，待测试
        // 查询桩站信息
        StationInfo stationInfo = this.stationInfoMapper.getById(gunEx.getStationId());
        // 查询公司信息
        CompanyInfo companyInfo = this.companyInfoMapper.get(stationInfo.getCompanyId());


        // 保存充电消费
        log.info("===> 保存充电消费");
        BigDecimal defaultValue = new BigDecimal(0);
        BigDecimal nowBalance = user.getBalance().subtract(order.getTotalPrice()); // 计算用户余额
        NewLifeSpendLog newLifeSpendLog = new NewLifeSpendLog();
        newLifeSpendLog.setMoneySn(order.getOrderSn()); // 设置流水号
        newLifeSpendLog.setOrderId(order.getId()); // 订单id
        newLifeSpendLog.setOrderSn(order.getOrderSn()); // 订单号
        newLifeSpendLog.setType(NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue()); // 使用类型
        newLifeSpendLog.setTypeRemark(NewLifeSpendLogTypeRemarkEnum.CONSUMPTION.getValue()); // 记录类型
        newLifeSpendLog.setAmount(order.getTotalPrice()); // 操作金额
        newLifeSpendLog.setUserId(userId);
        newLifeSpendLog.setUserName(user.getUserName());
        newLifeSpendLog.setUserMobile(user.getMobile());
        newLifeSpendLog.setUserAvatarUrl(user.getAvatarUrl());
        if(userUseCoupon != null){
            newLifeSpendLog.setCouponAmount(userUseCoupon.getPrice()); // 优惠券金额
            newLifeSpendLog.setCouponType(userUseCoupon.getType()); // 优惠券类型
        }else {
            newLifeSpendLog.setCouponAmount(defaultValue); // 优惠券金额
            newLifeSpendLog.setCouponType(0); // 优惠券类型
        }
        newLifeSpendLog.setUserOldBalance(user.getBalance()); // 用户充电前余额
        newLifeSpendLog.setUserGainAmount(order.getTotalPrice()); // 用户消费金额
        newLifeSpendLog.setUserType(UserTypeEnum.CAR.getValue()); // 用户类型
        newLifeSpendLog.setUserNowBalance(nowBalance); // 用户当前余额
        // 桩站和新活信息，设置为默认值
        newLifeSpendLog.setStationId(gunEx.getStationId());
        newLifeSpendLog.setStationName(gunEx.getStationName());
        newLifeSpendLog.setCommissionRation(companyInfo.getCommissionRation());// 抽佣比
        newLifeSpendLog.setServicePrice(stationInfo.getServicePrice()); // 服务费

        //计算桩站可得服务费
//        BigDecimal totalPrice = order.getTotalPrice(); //用户支付金额
        BigDecimal totalPrice = new BigDecimal(infoIn.getChargeAmount()); // 充电枪返回的总电费（目前判断为包含服务费）
        BigDecimal servicePrice = new BigDecimal(infoIn.getChargeEnergy()).multiply(stationInfo.getServicePrice());// 总服务费 = 充电电能*桩站服务费
        BigDecimal platServicePrice = servicePrice.multiply(companyInfo.getCommissionRation().divide(new BigDecimal(100)));// 平台可得服务费
        BigDecimal stationServicePrice = servicePrice.subtract(platServicePrice);// 桩站可得服务费
        BigDecimal chargePrice = totalPrice.subtract(servicePrice); // 充电费用

        newLifeSpendLog.setStationGainAmountCharge(chargePrice); //桩站可得电费
        newLifeSpendLog.setStationGainAmountService(stationServicePrice);// 桩站可得服务费
        newLifeSpendLog.setStationGainAmount(chargePrice.add(stationServicePrice)); //桩站可得总金额 = 桩站可得电费+桩站可得服务费
        newLifeSpendLog.setStationOldNoCheckAmount(stationInfo.getNoCheckBalance());// 桩站历史未结算金额
        newLifeSpendLog.setStationNowNoCheckAmount(stationInfo.getNoCheckBalance());// 桩站未结算金额
        newLifeSpendLog.setStationOldBalance(stationInfo.getUseBalance());// 桩站历史余额
        newLifeSpendLog.setStationNowBalance(stationInfo.getUseBalance().add(stationServicePrice.add(chargePrice)));// 桩站余额 = 桩站历史余额+桩站可得服务费+充电费用
        newLifeSpendLog.setNewLifeGainAmount(platServicePrice);// 新活可得金额
        newLifeSpendLog.setRemark("用户充电");
        newLifeSpendLog.setCreateTime(date);

        newLifeSpendLogMapper.insert(newLifeSpendLog);

        // 扣除用户表中用户余额
        log.info("===> 扣除用户表中用户余额");
        BigDecimal balance = user.getBalance();


        log.info("===> 用户余额明细：balance："+balance+" TotalPrice："+order.getTotalPrice());
        user.setBalance(balance.subtract(order.getTotalPrice()));
        log.info("===> 更新用户余额");
        userMapper.update(user);

        // 扣除用户账户表中余额
        log.info("===> 扣除用户账户表中余额");
        BigDecimal giveBalance = userAccount.getGiveBalance(); //赠送金额
        BigDecimal couponChargeBalance = userAccount.getCouponChargeBalance();// 有赠送金额的充值金额
        BigDecimal chargeBalance = userAccount.getChargeBalance();// 没有优惠的充值金额
        BigDecimal couponPrice = userUseCoupon==null?new BigDecimal(0):userUseCoupon.getPrice();
        log.info("===> giveBalance:"+giveBalance+" couponChargeBalance:"+couponChargeBalance+
                " chargeBalance:"+chargeBalance+" couponPrice:"+couponPrice);


        BigDecimal totalPriceTemp = order.getTotalPrice();
        if(totalPriceTemp.compareTo(couponPrice)>0 ){
            totalPriceTemp = totalPriceTemp.subtract(couponPrice);
        }

        boolean flag = true;

        // 当赠送金额小于用钱数时
        log.info("===> 当赠送金额小于用钱数时");
        if(giveBalance.compareTo(totalPriceTemp) < 0){
            totalPriceTemp = (totalPriceTemp.subtract(giveBalance));
            userAccount.setGiveBalance(new BigDecimal(0));
        }else {
            userAccount.setGiveBalance(giveBalance.subtract(totalPrice));
            flag = false;
        }
        log.info("===> giveBalance:"+giveBalance+" couponChargeBalance:"+couponChargeBalance+
                " chargeBalance:"+chargeBalance+" totalPriceTemp:"+totalPriceTemp+" couponPrice:"+couponPrice);

        // 当通参与优惠的实付金额小于用钱数时
        log.info("===> 当参与优惠的实付金额小于用钱数时");
        if(flag && couponChargeBalance.compareTo(totalPriceTemp) < 0){
            totalPriceTemp = (totalPriceTemp.subtract(couponChargeBalance));
            userAccount.setCouponChargeBalance(new BigDecimal(0));
        }else if(flag){
            userAccount.setCouponChargeBalance(couponChargeBalance.subtract(totalPrice));
            flag = false;
        }
        log.info("===> giveBalance:"+giveBalance+" couponChargeBalance:"+couponChargeBalance+
                " chargeBalance:"+chargeBalance+" totalPriceTemp:"+totalPriceTemp+" couponPrice:"+couponPrice);

        // 当通不参与优惠的实付金额大于等于用钱数时，可能最后得到负数
        log.info("===> 当不参与优惠的实付金额大于等于用钱数时，可能最后得到负数");
        if(flag){
            userAccount.setChargeBalance(chargeBalance.subtract(totalPriceTemp));
        }
        log.info("===> giveBalance:"+giveBalance+" couponChargeBalance:"+couponChargeBalance+
                " chargeBalance:"+chargeBalance+" totalPriceTemp:"+totalPriceTemp+" couponPrice:"+couponPrice);

        log.info("===> 更新用户账户");
        userAccountMapper.update(userAccount);

        if(userUseCoupon != null){
            // 将优惠券设置为已使用
            userCoupon.setStatus(1);
            userCoupon.setId(userUseCoupon.getId());
            userCouponMapper.update(userCoupon);
        }
        // 查询充电枪状态
        /*log.info("===> 查询充电枪状态");
        StationClientGun gunInfo = stationClientGunMapper.get(gunId);*/

        // 解除redis绑定状态
        log.info("===> 解除redis绑定状态");
        String key = getKey(Key_Prefix, gunId);
        String key2 = getKey(Key_Prefix, gunId)+"_"+userId;
        String gunKey = getKey(Gun_Status_Prefix, gunId);
        String userKey = Key_Prefix_User+userId;

        deleteRedisKey(key);
        deleteRedisKey(key2);
        deleteRedisKey(gunKey);
        deleteRedisKey(userKey);

        /*if(gunInfo.getStatus() == GunStatusEnum.FREE.getValue() ||
                gunInfo.getStatus() == GunStatusEnum.OFFLINE.getValue() ){

        }*/
        // 将结束后的数据发送到客户端
        // 将数据返回给客户端
        log.info("===> 将数据返回给客户端");
        ChargeEndOut out = new ChargeEndOut();
        out.setStatus(ChargeResultTypeEnum.END_RESULT.getValue());
        out.setClientGunId(chargeLog.getClientGunId());
//        out.setOrderSn(orderSn);

        // 清除redis
        String gunUserKey = getKey(Bind_Gun_User_Prefix,gunId);
        String gunOrderSnKey = getKeyByGun(Order_Sn_Prefix,gunId);
        deleteRedisKey(gunUserKey);
        deleteRedisKey(gunOrderSnKey);

        return out;

    }

    /**
     * 获取redis车位key
     * @param prefix key前缀
     * @param gunId 枪id
     * @return key
     */
    public String getKey(String prefix, Integer gunId){

        // 查询充电枪详情
        TruckSpaceEx detail = this.clientStationMapper.truckSpaceDetail(gunId);

        if(detail == null){
            log.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getGunId();

        if(key == null){
            log.info("===> 车位信息错误");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return key;
    }

    /**
     * 获取redis车位key
     * @param prefix key前缀
     * @param gunId 充电枪id
     * @return key
     */
    public String getKeyByGun(String prefix, Integer gunId){

        // 查询充电枪详情
        StationClientGunEx detail = this.stationClientGunMapper.getInfoByParams(gunId,
                null, null, null, null, null);

        if(detail == null){
            log.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getId();

        if(key == null){
            log.info("===> 车位信息错误");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return key;
    }

    /**
     * 删除key
     * @param key
     */
    private void deleteRedisKey(String key){
        if(RedisUtils.isExistRedis(key)){
            RedisUtils.deleteRedisValue(key);
        }
    }

}