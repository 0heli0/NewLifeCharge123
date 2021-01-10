package com.newlife.charge.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.google.common.collect.Lists;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.CouponTypeEnum;
import com.newlife.charge.core.enums.UserCouponStatusEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.UserCouponService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 用户优惠券领取和展示service类
 * <p>
 */
@Service
@Transactional
public class UserCouponServiceImpl implements UserCouponService {

    private Logger logger = LoggerFactory.getLogger(UserCouponServiceImpl.class);

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponUserReadRefMapper couponUserReadRefMapper;

    @Autowired
    private StationDetailMapper stationDetailMapper;

    @Autowired
    private CouponStationRefMapper couponStationRefMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public UserCouponOut pageList(UserCouponQueryIn query, Integer userId) {

        PageHelper.startPage(query.getPageNo(), query.getPageSize());

        UserCouponQuery queryMapper = dozer.map(query, UserCouponQuery.class);
        queryMapper.setUserId(userId);
        // 先查询通用充值券
        logger.info("===> 查询通用充值券");
        Page<UserCouponResult> generalChargePage = userCouponMapper.getGeneralChargeCoupon(queryMapper);

        // 再查询通用用电券
        logger.info("===> 查询通用用电券");
        Page<UserCouponResult> generalElectroPage = userCouponMapper.getGeneralElectroCoupon(queryMapper);

        // 指定桩站发布的优惠券
        logger.info("===> 分页查询所有指定桩站用电优惠券");
        Page<UserCouponResult> pointElectroPage = userCouponMapper.getAllPointElectroCoupon(queryMapper);

        // 判断当前用户优惠券是否可领
        List<UserCouponResult> generalChargeList = this.checkCouponIsUsable(generalChargePage.getResult(),userId);
        List<UserCouponResult> generalElectroList = this.checkCouponIsUsable(generalElectroPage.getResult(),userId);
        List<UserCouponResult> pointElectroList = this.checkCouponIsUsable(pointElectroPage.getResult(),userId);

        UserCouponOut result = new UserCouponOut();
        result.setGeneralCharge(generalChargeList);
        result.setGeneralElectro(generalElectroList);
        result.setPointElectro(pointElectroList);

        // 将已经查出的数据添加进入关联表中
        // 先查出本次查询到的数据
        List<Integer> couponIds= Lists.newArrayList();
        generalChargePage.getResult().stream().forEach(item -> couponIds.add(item.getId()));
        generalElectroPage.getResult().stream().forEach(item -> couponIds.add(item.getId()));
        pointElectroPage.getResult().stream().forEach(item -> couponIds.add(item.getId()));

        // 再根据userId查出关联表中的数据并保存
        saveReadRef(couponIds,userId);

        // 查询优惠券是否过期，如果过期，则修改优惠券状态为过期状态
        getOverdueCoupon();

        return result;
    }

    /**
     * 判断当前用户优惠券是否可领
     * @param list
     * @param userId
     * @return
     */
    private List<UserCouponResult> checkCouponIsUsable(List<UserCouponResult> list, Integer userId){

        // 查询该用户没有使用过的优惠券
       List<Integer> ids = userCouponMapper.getUseableCouponId(userId, UserCouponStatusEnum.UNUSED.getValue());
        list.stream().forEach(item ->{
            if(ids.contains(item.getId())){
                item.setTakeStatus(0);
            }else {
                item.setTakeStatus(1);
            }
        });


        return list;
    }

    /**
     * 查询优惠券表中优惠券过期且状态没有更新的数据，并修改成为过期的状态
     */
    private void getOverdueCoupon(){
        logger.info("===> 查询用户过期且未更改过期状态优惠券开始");
       List<Integer> ids = this.couponMapper.getOverdueCoupon();
       if(ids != null && ids.size()>0){
           Lock lock = new ReentrantLock(true);
           lock.lock();
           try {
               this.couponMapper.setOverdueCoupon(ids);
           }catch (BizException e){
                throw new BizException(ERROR.INTERNAL_ERROR);
           }finally {
               lock.unlock();
           }
       }
        logger.info("===> 查询用户过期且未更改过期状态优惠券结束");

    }

    /**
     * 查询用户优惠券中已过期，但未修改状态的，并修改
     */
    private void getOverdueUserCoupon(){
        logger.info("===> 查询过期且未更改过期状态优惠券开始");
        List<Integer> ids = this.couponMapper.getOverdueUserCoupon();
        if(ids != null && ids.size()>0){
            Lock lock = new ReentrantLock(true);
            lock.lock();
            try {
                this.userCouponMapper.setOverdueCoupon(ids);
            }catch (BizException e){
                throw new BizException(ERROR.INTERNAL_ERROR);
            }finally {
                lock.unlock();
            }
        }
        logger.info("===> 查询过期且未更改过期状态优惠券结束");

    }




    @Override
    public UserCouponOut stationPageList(UserStationCouponQueryIn query, Integer userId) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        // 指定桩站发布的优惠券

        UserCouponQuery query1 = new UserCouponQuery();
        query1.setUserId(userId);
        query1.setStationId(query.getStationId());


        logger.info("===> 查询通用用电券");
        UserCouponQuery queryIn = new UserCouponQuery();
        queryIn.setAutoIndex(0);
        queryIn.setUserId(userId);

        Page<UserCouponResult> generalElectroPage = userCouponMapper.getGeneralElectroCoupon(queryIn);

        logger.info("===> 查询指定桩站发布的优惠券");
        Page<UserCouponResult> pointElectroPage = userCouponMapper.getPointElectroCoupon(query1);

        // 判断当前用户优惠券是否可领
        List<UserCouponResult> generalElectroList = this.checkCouponIsUsable(generalElectroPage.getResult(),userId);
        List<UserCouponResult> pointElectroList = this.checkCouponIsUsable(pointElectroPage.getResult(),userId);

        UserCouponOut result = new UserCouponOut();
        result.setGeneralElectro(generalElectroList);
        result.setPointElectro(pointElectroList);

        List<Integer> couponIds= Lists.newArrayList();
        generalElectroPage.getResult().stream().forEach(item -> couponIds.add(item.getId()));
        pointElectroPage.getResult().stream().forEach(item -> {couponIds.add(item.getId());});

        // 根据userId查出关联表中的数据并保存
        saveReadRef(couponIds,userId);
        getOverdueCoupon();
        return result;
    }


    /**
     * 根据查询的数据，过滤并将不重复的数据保存到关系表中
     * @param couponIds
     */
    private void saveReadRef(List<Integer> couponIds, Integer userId){

        if(couponIds.size()==0){
            logger.info("===> 没有需要保存关联表的数据");
            return;
        }
        logger.info("===> 开始保存关联表");
        CouponUserReadRef readRef = new CouponUserReadRef();
        readRef.setUserId(userId);
        List<CouponUserReadRef> readRefs = couponUserReadRefMapper.getByQuery(readRef);
        List<Integer> readRefsInt= Lists.newArrayList();
        readRefs.stream().forEach(item -> readRefsInt.add(item.getCouponId()));
        // 从couponIds中过滤数据
        List<Integer> newCouponIds = couponIds.stream().filter(item -> !readRefsInt.contains(item)).collect(Collectors.toList());
        // 保存
        List<CouponUserReadRef> readRefList = Lists.newArrayList();
        newCouponIds.stream().forEach(item -> {
            CouponUserReadRef ref = new CouponUserReadRef();
            ref.setCouponId(item);
            ref.setUserId(userId);
            ref.setIsRead(1);
            ref.setCreateTime(new Date());
            readRefList.add(ref);
        });
        if(readRefList != null && readRefList.size() > 0){
            couponUserReadRefMapper.batchInsert(readRefList);

        }
        logger.info("===> 关联表保存结束");
    }

    @Override
    public void take(TakeCouponIn takeIn, Integer userId) {

        // 由于优惠券数量有限故操作时加入公平锁
        logger.info("===> 加入锁");
        Lock lock = new ReentrantLock(true);
        lock.lock();
        try {
            // 根据限领张数查看该优惠券是否可领
            logger.info("===> 根据限领张数查看该优惠券是否可领，userId:"+userId);
            Coupon couponInfo = couponMapper.get(takeIn.getId());
            if(couponInfo == null){
                throw new BizException(ERROR.DATA_NOT_FOUND);
            }

            // 领取时间已过
            if(couponInfo.getCollectEndTime().getTime()<System.currentTimeMillis()){
                throw new BizException(ERROR.COUPON_FINISH);
            }

            // 查看该券是否已经领取，且存在未使用的
            int isUnUsed = this.userCouponMapper.checkCouponLimitNum(takeIn.getId(), UserCouponStatusEnum.UNUSED.getValue(), userId);
            if(isUnUsed > 0){
                throw new BizException(ERROR.COUPON_UN_USE);
            }

            // 查找可领优惠券的剩余数量,当剩余优惠券数量等于0时,提示优惠券已经被抢完
            logger.info("===> 查找可领优惠券的剩余数量");
            int leftNumber = this.userCouponMapper.getCouponLeftNumber(takeIn.getId());
            if(leftNumber<=0){
                throw new BizException(ERROR.COUPON_NONE);
            }

            // 领取数大于等于限领
            int takeNumber = this.userCouponMapper.checkCouponLimitNum(takeIn.getId(), null, userId);
            if (takeNumber >= takeIn.getLimitNumber()){
                throw new BizException(ERROR.COUPON_LIMIT);
            }
            if(takeNumber >= couponInfo.getLimitNumber()){
                throw new BizException(ERROR.COUPON_LIMIT);
            }

            // 当满足领取条件时,领取优惠券
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setCouponId(takeIn.getId());
            userCoupon.setCreateTime(new Date());
            userCoupon.setStatus(UserCouponStatusEnum.UNUSED.getValue());
            userCoupon.setUserId(userId);
            userCoupon.setSerialNumber("");
            userCoupon.setOrderSn("");

            // 新增用户领取记录
            logger.info("===> 新增用户领取记录");
            this.userCouponMapper.insert(userCoupon);

            // 将对应的优惠券剩余数量减1
            leftNumber = leftNumber -1;

            // 更新优惠券表
            Coupon coupon = new Coupon();
            coupon.setId(takeIn.getId());
            coupon.setLeftNumber(leftNumber);
            // 将优惠券表剩余数量减1
            logger.info("===> 将优惠券表剩余数量减1");
            this.userCouponMapper.updateCoupon(coupon);

        }finally {
            lock.unlock();
            logger.info("===> 释放锁");
        }
    }

    @Override
    public PageInfo<UserCouponPageOut> userCouponPage(UserCouponListIn queryIn, Integer userId) {

        // 先查询优惠券是否过期
        getOverdueUserCoupon();

        PageHelper.startPage(queryIn.getPageNo(),queryIn.getPageSize());
        if(queryIn.getStatus() == 2){
            queryIn.setStatus(UserCouponStatusEnum.OVERDUE.getValue());
        }

        Page<UserCouponPageOut> result = this.userCouponMapper.userCouponPage(queryIn.getStatus(),userId);

        // 将冻结状态归到使用中
        result.getResult().forEach(item ->{
            if(UserCouponStatusEnum.LOCKED.getValue().equals(item.getStatus())){
                item.setStatus(UserCouponStatusEnum.USED.getValue());
            }
        });

        return new PageInfo<>(result.getPageNum(),result.getPageSize(),result.getTotal(),result.getResult());
    }

    @Override
    public UserCouponDetail userCouponDetail(InfoIn infoIn) {
        UserCouponDetail result = this.userCouponMapper.userCouponDetail(infoIn.getId());
        if(result == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        if(result.getStatus().equals(UserCouponStatusEnum.USED.getValue()) && StringUtil.isNotEmpty(result.getOrderSn())){
            Order order = orderMapper.getBySn(result.getOrderSn());
            result.setUseTime(order.getPayTime());
        }

        // 查询通用券是否指定唯一桩站
        if(result.getType().equals(CouponTypeEnum.Electro_Type.getValue())) {
            List<CouponStationRef> refs = this.couponStationRefMapper.getByCouponId(result.getCouponId());
            if (refs.size() == 1){
                result.setType(CouponTypeEnum.Station_Electro_Type.getValue());
            }
        }
        // 当是桩站发放的优惠券时，查询经纬度
        if(result.getType() != null &&
                result.getType().equals(CouponTypeEnum.Station_Electro_Type.getValue()) &&
                result.getStationId()!= null && result.getStationId() > 0){
            StationDetail stationDetail = this.stationDetailMapper.getByStationId(result.getStationId());
            result.setLat(stationDetail.getLat());
            result.setLng(stationDetail.getLng());
            result.setAddress(stationDetail.getAddress());
        }
        return result;
    }


    @Override
    public PageInfo<CouponUsePageOut> usePage(CouponUseQueryIn queryIn) {
        PageHelper.startPage(queryIn.pageNo,queryIn.getPageSize());

        Page<CouponUsePageOut> result =  this.userCouponMapper.usePage(queryIn);
        return new PageInfo<>(result.getPageNum(),result.getPageSize(),result.getTotal(),result.getResult());
    }
}
