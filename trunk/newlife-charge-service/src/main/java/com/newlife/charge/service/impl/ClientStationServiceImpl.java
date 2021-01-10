package com.newlife.charge.service.impl;

import com.google.common.collect.Lists;
import com.newlife.charge.common.*;
import com.newlife.charge.common.TelCharg.TelChargApi;
import com.newlife.charge.common.weixin.WeiXinUtils;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.exModel.ClientStationDetailEx;
import com.newlife.charge.core.domain.exModel.ClientStationTimePriceEx;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.domain.exModel.TruckSpaceEx;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.ClientStationService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


@Service
@Transactional
public class ClientStationServiceImpl implements ClientStationService {

    private Logger logger = LoggerFactory.getLogger(ClientStationServiceImpl.class);

    // 绑定车位前缀
    private static final String Lot_key_Prefix = "binding_lot_id_";

    // 绑定充电枪前缀
    private static final String Key_Prefix = "parkingLot_";

    // 用户绑定枪
    private static final String Key_Prefix_User = "user_parkingLot_";

    // 绑定充电枪状态前缀
    private static final String Gun_Status_Prefix = "parkingLot_gun_status_";

    // 将枪和订单号绑定
//    private static final String Order_Sn_Prefix = "orderSnGun_";

    @Autowired
    private Mapper dozer;

    @Autowired
    private ClientStationMapper clientStationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ChargeLogMapper chargeLogMapper;

    @Autowired
    private StationClientGunMapper stationClientGunMapper;

    @Autowired
    private StationParkingLotMapper stationParkingLotMapper;

    @Autowired
    private StationTimePriceMapper stationTimePriceMapper;

    @Autowired
    private StationInfoMapper stationInfoMapper;

    @Autowired
    private StationClientMapper stationClientMapper;

    @Override
    public ClientStationDetailOut stationDetail(InfoIn infoIn) {
        ClientStationDetailEx result = this.clientStationMapper.stationDetail(infoIn.getId());

        ClientStationDetailOut detail = dozer.map(result, ClientStationDetailOut.class);

        // 计算电价
        BigDecimal price = CommonUtil.calculatePrice(result.getChargePrice(),result.getServicePrice(),result.getIncrease());
        detail.setPrice(price);

        //判断是否全电价一致
        if(result.getIncreaseCount() > 0){
            detail.setIsTimePrice(1);
        }
        return detail;
    }

    @Override
    public List<ClientStationTimePriceOut> timePriceDetail(InfoIn infoIn) {
        List<ClientStationTimePriceEx> result = this.clientStationMapper.timePriceDetail(infoIn.getId());

        Map<Integer,List<String>> timeMap = new HashMap<>();
        result.forEach(item ->{
            // 计算实时电价
            logger.info(item.getChargePrice()+"---"+item.getServicePrice()+"---"+item.getIncrease());
            item.setPrice(CommonUtil.calculatePrice(item.getChargePrice(),new BigDecimal(0),item.getIncrease()));

            if(timeMap.containsKey(item.getTimeNo())){
                String timeBegin = item.getTimeBegin().toString();
                String timeEnd = item.getTimeEnd().toString();
                List<String> times = timeMap.get(item.getTimeNo());
                times.add(timeBegin+"-"+timeEnd);
                timeMap.put(item.getTimeNo(),times);
            }else {
                String timeBegin = item.getTimeBegin().toString();
                String timeEnd = item.getTimeEnd().toString();
                List<String> times = Lists.newArrayList();
                times.add(timeBegin+"-"+timeEnd);
                timeMap.put(item.getTimeNo(), times);
            }
        });

        List<ClientStationTimePriceOut> out = BeanMapper.mapList(result, ClientStationTimePriceOut.class);

        out.forEach(item ->{
            if(timeMap.containsKey(item.getTimeNo())){
                item.setTimes(timeMap.get(item.getTimeNo()));
            }
        });

        // 根据时段去除重复数据
        out = out.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(ClientStationTimePriceOut::getTimeNo))
                        ), ArrayList::new
                )
        );

        return out;
    }

    @Override
    public List<ClientTruckSpaceOut> truckSpaceList(InfoIn infoIn) {
        updateLotStatus();
        // 获取车位及其绑定的充电枪详情
        List<TruckSpaceEx> list = this.clientStationMapper.truckSpaceList(infoIn.getId());

        list.forEach(item ->{
            // 计算电价
            BigDecimal price = CommonUtil.calculatePrice(item.getChargePrice(),item.getServicePrice(),item.getIncrease());
            item.setPrice(price);

            // 先查看缓存，修改充电枪状态,改为自定义状态(0:不可用,1:可用)
            String key = Key_Prefix+item.getStationId()+"_"+item.getGunId();
            if(RedisUtils.isExistRedis(key)){
                item.setStatus(0);
            }else if(item.getStatus() == GunStatusEnum.FREE.getValue() &&
                    item.getLotStatus() == StationParkingLotStatusEnum.FREE.getValue()){
                item.setStatus(1);
            }else {
                item.setStatus(0);
            }
        });

        List<ClientTruckSpaceOut> result = BeanMapper.mapList(list, ClientTruckSpaceOut.class);

        return result;
    }

    @Override
    public ClientTruckSpaceOut truckSpaceDetail(InfoIn infoIn, Integer userId) {
        updateLotStatus();
        TruckSpaceEx detail = this.clientStationMapper.truckSpaceDetail(infoIn.getId());

        if(detail == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        // 修改充电枪状态,改为自定义状态(0:不可用,1:可用)
        if(detail.getStatus() == GunStatusEnum.FREE.getValue()&&
                detail.getLotStatus() == StationParkingLotStatusEnum.FREE.getValue()){
            detail.setStatus(1);
        }else {
            detail.setStatus(0);
        }

        // 计算电价
        BigDecimal price = CommonUtil.calculatePrice(detail.getChargePrice(),detail.getServicePrice(),detail.getIncrease());
        detail.setPrice(price);

        ClientTruckSpaceOut result = dozer.map(detail, ClientTruckSpaceOut.class);
        result.setGunCode(detail.getHeadCode()+detail.getGunCode());

        // 查询是否存在时段电价
        List<StationTimePrice> timePriceList = this.stationTimePriceMapper.getByStationId(detail.getStationId());
        if(timePriceList != null && timePriceList.size()>0){
            result.setPriceStatus(1);
        }else {
            result.setPriceStatus(0);
        }

        // 查询用户账户余额
        User User = this.userMapper.get(userId);
        // 计算余额
        BigDecimal balance = User.getBalance();
        result.setBalance(balance);
        return result;
    }

    @Override
    public BindingParkingOut bindingParking(InfoIn infoIn,Integer userId) {
        updateLotStatus();
        Lock lock = new ReentrantLock(true);
        lock.lock();
        try {
            BindingParkingOut out = new BindingParkingOut();
            // 查询充电枪详情
            TruckSpaceEx detail = this.clientStationMapper.truckSpaceDetail(infoIn.getId());
            if(detail == null){
                throw new BizException(ERROR.DATA_NOT_FOUND);
            }
            if(detail.getStatus() == GunStatusEnum.FREE.getValue() &&
                    detail.getLotStatus() == StationParkingLotStatusEnum.FREE.getValue()){
                out.setStatus(1);
            }else {
                out.setStatus(0);
                return out;
            }

            // 查询用户之是否有充电为完成的订单
            List<ChargeLog> chargeLogs = this.chargeLogMapper.getByParams(userId,
                    null,
                    null,
                    ChargeLogStatusEnum.CHARGE_ING.getValue());

            // 存在则抛出异常,提示还有未结束订单
            if(chargeLogs != null && chargeLogs.size()>0){
                throw new BizException(ERROR.OPEN_ORDER);
            }

            // 查询用户账户余额
            User user = this.userMapper.get(userId);

            if(user == null){
                throw new BizException(ERROR.DATA_NOT_FOUND);
            }

            BigDecimal price =user.getBalance();

            if(price.compareTo(new BigDecimal(10)) == -1){
                out.setIsNeedCharge(0);
                return out;
            }else {
                out.setIsNeedCharge(1);
            }
            // 通过用户id绑定
            String userKey = Key_Prefix_User+userId;
            if(!RedisUtils.isExistRedis(userKey)){
                RedisUtils.setRedisValue(userKey,detail.getGunId()+"",15,TimeUnit.MINUTES);
                out.setIsBindOther(0);
            }else {
                out.setIsBindOther(1);
                throw new BizException(ERROR.OPEN_ORDER);
            }

            // 之前验证通过后将车位锁定(通过redis设置)
            String key = Key_Prefix+detail.getStationId()+"_"+detail.getGunId();
            String key2 = Key_Prefix+detail.getStationId()+"_"+detail.getGunId()+"_"+userId;
            String lotKey = Lot_key_Prefix+detail.getId();

            // 如果存在值，则进行状态判断，不存在则新增
            if(RedisUtils.isExistRedis(key)
                    && RedisUtils.getRedisValue(key).equalsIgnoreCase(StationParkingLotStatusEnum.BOOKED.getValue()+"")){

                // 如果预约人与当前预约人一致，则返回成功
                if(RedisUtils.isExistRedis(key2) && Integer.parseInt(RedisUtils.getRedisValue(key2)) == userId){
                    return out;
                }
                // 如果车位正在使用则将状态改为被使用并退出
                out.setStatus(0);
                return out;

            }

            RedisUtils.setRedisValue(lotKey, StationParkingLotStatusEnum.BOOKED.getValue()+"", 15, TimeUnit.MINUTES);

            StationParkingLot lot = stationParkingLotMapper.get(detail.getId());
            lot.setStatus(StationParkingLotStatusEnum.BOOKED.getValue());
            stationParkingLotMapper.update(lot);
            RedisUtils.setRedisValue(key, StationParkingLotStatusEnum.BOOKED.getValue()+"",15,TimeUnit.MINUTES);
            RedisUtils.setRedisValue(key2, userId+"",15,TimeUnit.MINUTES);

            // 设置一个定时任务, 如果在定时任务内状态没有改变, 则解锁车位
//            ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(1);
            /*scheduled.schedule(new Runnable() {
                @Override
                public void run() {
                    logger.info("====> 执行定时任务, 解锁车位");

                    // 先查询车位状态是否正常
                    StationParkingLot lotInfo = stationParkingLotMapper.get(detail.getId());

                    logger.info("===> 状态: "+lotInfo.getStatus() +
                            ", 当前时间: "+ new Date());

                    if( lotInfo != null &&
                            lotInfo.getStatus() == StationParkingLotStatusEnum.BOOKED.getValue()){

                        StationParkingLot parkingLot = new StationParkingLot();
                        parkingLot.setId(lotInfo.getId());
                        parkingLot.setStatus(StationParkingLotStatusEnum.FREE.getValue());
                        logger.info("====> 开始解锁车位,车位Id: "+lotInfo.getId());
                        stationParkingLotMapper.update(parkingLot);

                    }else {
                        logger.info("====> 解锁车位失败,状态异常");
                    }

                }
            },15, TimeUnit.MINUTES);*/

            return out;

        }finally {
            lock.unlock();
            logger.info("====> 释放锁");
        }
    }

    @Override
    public int cancelBinding(InfoIn infoIn,Integer userId) {
        updateLotStatus();
        String key = getKey(Key_Prefix,infoIn.getId());
        String key2 = getKey(Key_Prefix,infoIn.getId())+"_"+userId;
        String userKey = Key_Prefix_User+userId;


        if (RedisUtils.isExistRedis(userKey)){
            RedisUtils.deleteRedisValue(userKey);
        }else {
            return StationParkingLotStatusEnum.FREE.getValue();
        }
        // 查看枪状态是否为连接中，如果是，则通知充电枪停止充电
        // 查询充电枪
        StationClientGunEx gunEx = stationClientGunMapper.getInfoByParams(
                infoIn.getId(),null,null,null,null, null);


        if(RedisUtils.isExistRedis(key)
                && RedisUtils.getRedisValue(key).equalsIgnoreCase(StationParkingLotStatusEnum.BOOKED.getValue()+"")){

            RedisUtils.deleteRedisValue(key);

            if(RedisUtils.isExistRedis(key2)){
                RedisUtils.deleteRedisValue(key2);
            }


        }

        String lotKey = Lot_key_Prefix+gunEx.getStationParkingLotId();
        if(RedisUtils.isExistRedis(lotKey)){
            RedisUtils.deleteRedisValue(lotKey);
        }

        // 重置车位状态
        StationParkingLot lot = stationParkingLotMapper.get(gunEx.getStationParkingLotId());
        lot.setStatus(StationParkingLotStatusEnum.FREE.getValue());
        stationParkingLotMapper.update(lot);

        // 关闭webSocket
        WebSocketServerImpl.closeByuser(userId+"");


        return StationParkingLotStatusEnum.FREE.getValue();
    }

    @Override
    public void plugInGun(PlugInGunIn infoIn) {

        // 先判断充电枪是否连接成功
        if(infoIn.getStatus() == 0){
            throw new BizException(ERROR.STATION_CLIENT_GUN_CONNECT_FAIL);
        }

        // 判断车位是否依然绑定，是则将绑定状态设为长时间（具体时间待定），更新状态为正在使用
        String key = getKey(Key_Prefix,infoIn.getGunId());

        if(RedisUtils.isExistRedis(key)
                && RedisUtils.getRedisValue(key).equalsIgnoreCase(StationParkingLotStatusEnum.BOOKED.getValue()+"")){

            RedisUtils.setRedisValue(key,StationParkingLotStatusEnum.USED.getValue()+"",24,TimeUnit.HOURS);

            // 绑定充电枪状态
            String gunKey = getKey(Gun_Status_Prefix,infoIn.getGunId());
            RedisUtils.setRedisValue(gunKey,infoIn.getStatus()+"",24,TimeUnit.HOURS);

        }
    }

    @Override
    public ChargeBeginOut chargeBegin(ChargeParamsIn infoIn, Integer userId) {

        checkGunStatus(infoIn.getGunId());

        // 生成充电订单
        Order order = new Order();
        String orderSn = TelChargApi.getNewOrderSn(NewLifeSpendLogTypeStrEnum.CHARGE_CONSUMPTION.getDescription());
        order.setOrderSn(orderSn);
        order.setOrderType(OrderTypeEnum.CHARGE.getValue());
        order.setUserId(userId);
        order.setPayStatus(PayStatusEnum.NONE.getValue());
        order.setPayType(PayTypeEnum.ACCOUNT_BALANCE.getValue());
        order.setSumPrice(new BigDecimal(0));
        order.setCouponPrice(new BigDecimal(0));
        order.setTotalPrice(new BigDecimal(0));
        order.setPayTime(new Date());
        order.setCreateTime(new Date());

        orderMapper.insert(order);

        // 查询用户数据
        User user = userMapper.get(userId);
        // 查询充电枪
        StationClientGunEx gunEx = stationClientGunMapper.getInfoByParams(
                infoIn.getGunId(),null,null,null,null, null);

        String balance = user.getBalance().multiply(WeiXinUtils.switchToCent).setScale(0,BigDecimal.ROUND_HALF_UP).toString();

        // 通知充电枪开始充电
        StartStopCommandIn startStopCommandIn = new StartStopCommandIn();
        startStopCommandIn.setStartStopFlag("1"); // 启动标记
        startStopCommandIn.setBalance(balance);
        startStopCommandIn.setUserNo(user.getMobile());
        startStopCommandIn.setTransactionNo(order.getId()+"");
        startStopCommandIn.setChargeMode(0+"");
        startStopCommandIn.setGunCode(gunEx.getCode());
        startStopCommandIn.setStationClientCode(gunEx.getStationClientCode());

        // TODO:模拟环境，暂时注释启动接口
//        String chargeResult = stationClientGunService.startCharge(startStopCommandIn);

        // 将订单号和充电枪绑定到redis
//        RedisUtils.setRedisValue(Order_Sn_Prefix+orderSn,infoIn.getGunId()+"", 24,TimeUnit.HOURS);

        // 根据车位绑定订单号
//        RedisUtils.setRedisValue(getKeyByGun(Order_Sn_Prefix, infoIn.getGunId()), orderSn, 24, TimeUnit.HOURS);

        // 生成充电记录
        ChargeLog chargeLog = new ChargeLog();
        BigDecimal defaultPrice = new BigDecimal(0);

        // 查询桩站信息
        StationInfo stationInfo = this.stationInfoMapper.get(gunEx.getStationId());

        // 查询桩信息
        StationClient stationClient = this.stationClientMapper.get(gunEx.getStationClientId());

        chargeLog.setOrderId(order.getId()); // 订单id
        chargeLog.setOrderSn(orderSn); // 订单号
        chargeLog.setUserId(userId);
        chargeLog.setStationId(gunEx.getStationId());
        chargeLog.setStationName(gunEx.getStationName());
        chargeLog.setClientId(gunEx.getStationClientId());
        chargeLog.setClientCode(gunEx.getStationClientCode());
        chargeLog.setClientChargeType(stationClient.getChargeType()); // 充电桩类型
        chargeLog.setParkingLotId(gunEx.getStationParkingLotId());
        chargeLog.setParkingLotCode(gunEx.getStationParkingLotCode());
        chargeLog.setClientGunId(gunEx.getId());
        chargeLog.setClientGunCode(gunEx.getCode());
        chargeLog.setBeginTime(new Date());
        chargeLog.setEndTime(new Date());
        chargeLog.setDegreePredict(defaultPrice);
        chargeLog.setDegreeReal(defaultPrice);
        chargeLog.setUserCouponId(0);
        chargeLog.setPriceCoupon(defaultPrice);
        chargeLog.setChargePrice(stationInfo.getChargePrice()); // 基础价格
//        chargeLog.setChargePrices(); // 充电期间的价格
        chargeLog.setServicePrice(stationInfo.getServicePrice()); // 服务费
        chargeLog.setPriceReal(defaultPrice);
        chargeLog.setVehicleType("");
        chargeLog.setPlateNumber("");
        chargeLog.setStatus(ChargeLogStatusEnum.CHARGE_ING.getValue());
        chargeLog.setCreateTime(new Date());
        chargeLog.setStopType(ChargeStopTypeEnum.CHARGE_ING.getValue());

        // 上面注释的暂时没有，先写默认，最后结算时写入
        chargeLog.setChargePrices(""); // 充电期间的价格

        chargeLogMapper.insert(chargeLog);

        // 获取当前信息
        ChargeBeginOut out = new ChargeBeginOut();
        out.setClientGunId(gunEx.getId());
        out.setOrderSn(order.getOrderSn());

        return out;
    }

    @Override
    public void stopCharge(ChargeParamsIn infoIn, Integer userId, Integer type) {

        // 查询订单
        Order order = this.orderMapper.getBySn(infoIn.getOrderSn());
        if(order == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        // 查询用户数据
        User user = userMapper.get(userId);
        String balance = user.getBalance().multiply(WeiXinUtils.switchToCent).scale()+"";

        // 查询充电枪
        StationClientGunEx gunEx = stationClientGunMapper.getInfoByParams(
                infoIn.getGunId(),null,null,null,null, null);

        // 通知充电枪停止充电
        StartStopCommandIn startStopCommandIn = new StartStopCommandIn();
        startStopCommandIn.setStartStopFlag("2"); // 停止标记
        startStopCommandIn.setBalance(balance);
        startStopCommandIn.setUserNo(user.getMobile());
        startStopCommandIn.setTransactionNo(order.getId()+"");
        startStopCommandIn.setChargeMode(0+"");
        startStopCommandIn.setGunCode(gunEx.getCode());
        startStopCommandIn.setStationClientCode(gunEx.getStationClientCode());
        //TODO: 暂时不调用远程接口
//        String chargeResult = stationClientGunService.stopCharge(startStopCommandIn);

        // 更新结束类型
        if(type != null){
            ChargeLog chargeLog = this.chargeLogMapper.getByOrderSn(infoIn.getOrderSn());
            if(type == 0){
                chargeLog.setStopType(ChargeStopTypeEnum.MANUAL_STOP.getValue());
            }else if (type == 1){
                chargeLog.setStopType(ChargeStopTypeEnum.NO_MONEY_STOP.getValue());
            }
            this.chargeLogMapper.update(chargeLog);
        }


        // 解析返回结果，通过结果判断是否成功

        // 停止成功后操作
//        chargeStopSuccess(order,infoIn.getGunId(),infoIn.getId(),userId,infoIn.getStatus());

    }


    @Override
    public ChargeStatusOut chargeStatus(Integer userId) {

        // 查询该用户最近的一条记录
        ChargeLog chargeLog = chargeLogMapper.getByUserId(userId);

        if(chargeLog != null){
            ChargeStatusOut out = dozer.map(chargeLog, ChargeStatusOut.class);
            return out;
        }

        return null;
    }

    @Override
    public ChargeBeginOut getTimeData(ChargeTimeDataIn infoIn, Integer userId) {
        // 查询充电枪中实时数据信息
        StationClientGun gunInfo = stationClientGunMapper.get(infoIn.getGunId());

        String chargeTimeStr = gunInfo.getChargeTime();
        Long chargeTimeNum = 0L;
        if(StringUtil.isNotEmpty(chargeTimeStr)){
            chargeTimeNum = Long.parseLong(chargeTimeStr);
        }

        String chargeTime = DateUtils.secondToTime(chargeTimeNum);

        gunInfo.setChargeTime(chargeTime);

        // 查询用户信息
        User user = userMapper.get(userId);
        BigDecimal limitPrice = new BigDecimal(10);

        String chargeAmountStr = StringUtil.isNotEmpty(gunInfo.getChargeAmount())?gunInfo.getChargeAmount():"0";

        BigDecimal chargeAmount = new BigDecimal(chargeAmountStr); // 实时充电金额

        BigDecimal leftBalance = user.getBalance().subtract(chargeAmount);

        // 查询充电记录
        ChargeLog chargeLog = this.chargeLogMapper.getByMobile(user.getMobile());
        if(chargeLog == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        // 计算余额小于等于10元时，终止充电，生成充电账单，扣除用户余额
        if(leftBalance.compareTo(limitPrice) <= 0){
            ChargeParamsIn paramsIn = new ChargeParamsIn();
            paramsIn.setGunId(infoIn.getGunId());
            paramsIn.setId(gunInfo.getStationParkingLotId());
            paramsIn.setOrderSn(chargeLog.getOrderSn());
            paramsIn.setStatus(2);
            // 进入停止充电操作
            stopCharge(paramsIn, userId, 1);

        }
        // 根据充电枪id查询车位详情信息
        StationGunAddressDetail addressDetail = stationClientGunMapper.getStationGunAddressDetail(gunInfo.getId());

        ChargeBeginOut out = dozer.map(gunInfo, ChargeBeginOut.class);
        out.setClientGunId(gunInfo.getId());
        out.setStatus(chargeLog.getStatus());
        out.setOrderSn(chargeLog.getOrderSn());
        out.setGunCode(chargeLog.getClientGunCode());
        out.setLotCode(chargeLog.getParkingLotCode());
        out.setAddress(addressDetail.getAddress());

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
            logger.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getGunId();

        if(key == null){
            logger.info("===> 车位信息错误");
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
            logger.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getId();

        if(key == null){
            logger.info("===> 车位信息错误");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return key;
    }

    /**
     * 判断充电枪是否连接成功
     * @param gunId 枪id
     */
    private void checkGunStatus(Integer gunId){
        // 判断充电枪是否进入充电状态
//        String key = getKey(Key_Prefix, lotId);
        String gunKey = getKey(Gun_Status_Prefix, gunId);

        /*
        RedisUtils.isExistRedis(key) && RedisUtils.isExistRedis(gunKey) &&
                (StationParkingLotStatusEnum.USED.getValue() + "").equalsIgnoreCase(RedisUtils.getRedisValue(key)) &&
                "1".equalsIgnoreCase(RedisUtils.getRedisValue(gunKey)
         */
        if (RedisUtils.isExistRedis(gunKey) &&
                (GunStatusEnum.LINKING.getValue()+"").equalsIgnoreCase(RedisUtils.getRedisValue(gunKey))
        ) {
            return;
        }else {
            throw new BizException(ERROR.STATION_CLIENT_GUN_CONNECT_FAIL);
        }
    }

    @Override
    public ClientTruckSpaceOut jumpToTruckSpaceDetail(Integer userId) {
        // 通过用户查询redis是否存在绑定关系
        String userKey = Key_Prefix_User+userId;
        if(!RedisUtils.isExistRedis(userKey)){
            return null;
        }
        Integer gunId = Integer.parseInt(RedisUtils.getRedisValue(userKey));
        InfoIn  infoIn = new InfoIn();
        infoIn.setId(gunId);
        ClientTruckSpaceOut out = this.truckSpaceDetail(infoIn,userId);
        out.setLotStatus(1);

        return out;
    }

    @Override
    public ChargeEndOut getChargeLogData(String orderSn, Integer userId) {
        ChargeLog chargeLog = new ChargeLog();
        if(StringUtil.isNotEmpty(orderSn)){
            chargeLog = chargeLogMapper.getByOrderSn(orderSn);
        }else {
            chargeLog = chargeLogMapper.getByUserId(userId);
        }

        if(chargeLog == null){
            return null;
        }
        ChargeEndOut out = dozer.map(chargeLog, ChargeEndOut.class);

        // 计算时间
        long time = 0L;
        if(chargeLog.getEndTime() != null && chargeLog.getBeginTime() != null ){
            time = DateUtils.getSecondTimestamp(chargeLog.getEndTime()) - DateUtils.getSecondTimestamp(chargeLog.getBeginTime());
        }
        out.setChargeTime(DateUtils.secondToTime(time));

        out.setChargeEnergy(chargeLog.getDegreeReal().toString());
        out.setChargeAmount(chargeLog.getPriceReal().add(chargeLog.getPriceCoupon()));
        out.setCouponPrice(chargeLog.getPriceCoupon());
        out.setActualAmount(chargeLog.getPriceReal());
        out.setChargeTimePrice(chargeLog.getChargePrices());
        out.setGunCode(chargeLog.getClientGunCode());
        out.setStopType(chargeLog.getStatus());

        // 查询地址
        StationGunAddressDetail addressDetail = stationClientGunMapper.getStationGunAddressDetail(chargeLog.getClientGunId());
        if(addressDetail != null){
            out.setAddress(addressDetail.getAddress());
            out.setLotCode(addressDetail.getLotCode());
            out.setStationName(addressDetail.getStationName());
            out.setLat(addressDetail.getLat());
            out.setLng(addressDetail.getLng());
        }


        return out;
    }

    /**
     * 触发车位解绑
     */
    public void updateLotStatus(){
        logger.info("===> 触发解绑车位任务");
        // 查询绑定了的车位
        StationParkingLot lot = new StationParkingLot();
        lot.setStatus(StationParkingLotStatusEnum.BOOKED.getValue());
        List<StationParkingLot> list = stationParkingLotMapper.getByParams(lot);
        if(list != null && list.size() > 0){
            list.forEach(item ->{
                String lotKey = Lot_key_Prefix+item.getId();
                if(!RedisUtils.isExistRedis(lotKey)){
                    item.setStatus(StationParkingLotStatusEnum.FREE.getValue());
                    stationParkingLotMapper.update(item);
                }
            });
        }
    }

}
