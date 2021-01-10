package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.UserCouponMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.Coupon;
import com.newlife.charge.core.domain.UserCoupon;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.dao.common.CrudRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserCouponMapper  extends CrudRepository<UserCoupon> {

    /**
     * 分页查询通用充值优惠券
     * @param
     * @return
     */
    Page<UserCouponResult> getGeneralChargeCoupon(UserCouponQuery query);

    /**
     * 分页查询通用用电优惠券
     * @param
     * @return
     */
    Page<UserCouponResult> getGeneralElectroCoupon(UserCouponQuery query);

    /**
     * 分页查询所有指定桩站用电优惠券
     * @param
     * @return
     */
    Page<UserCouponResult> getAllPointElectroCoupon(UserCouponQuery query);
    /**
     * 分页查询指定桩站用电优惠券
     * @param
     * @return
     */
    Page<UserCouponResult> getPointElectroCoupon(UserCouponQuery query);

    /**
     * 查找可领优惠券的剩余数量
     * @param id
     * @return
     */
    int getCouponLeftNumber(@Param("id")Integer id);

    /**
     * 根据限领张数查看该优惠券是否可领
     * @param id
     * @param status
     * @return
     */
    int checkCouponLimitNum(@Param("id") Integer id,
                            @Param("status") Integer status,
                            @Param("userId") Integer userId);

    /**
     * 根据限领张数查看该优惠券是否已经使用
     * @param id
     * @return
     */
    int checkCouponisUsed(@Param("id") Integer id);

    /**
     * 根据状态获取优惠券id
     * @param userId
     * @return
     */
    List<Integer> getUseableCouponId(@Param("userId") Integer userId, @Param("status") Integer status);

    /**
     * 更新对应的优惠券表,将剩余数量减一
     * @param coupon
     * @return
     */
    int updateCoupon(Coupon coupon);

    /**
     * 通过状态分页查找已领取的优惠券
     * @param status
     * @param userId
     * @return
     */
    Page<UserCouponPageOut> userCouponPage(@Param("status")Integer status, @Param("userId")Integer userId);

    /**
     * 通过状态和手机号分页查找指定优惠券使用情况
     * @param queryIn
     * @return
     */
    Page<CouponUsePageOut> usePage(CouponUseQueryIn queryIn);

    /**
     * 查看已经领取的优惠券的详情
     * @param id
     * @return
     */
    UserCouponDetail userCouponDetail(Integer id);

    /**
     * @Description: 查询 优惠劵领取的用户
     * @Author: Linzq
     * @CreateDate:  2019/5/13 0013 11:35
     */
    List<UserCoupon> getByCouponId(@Param("couponId")Integer couponId);

    /**
     * 车主端 获取可用的最大优惠券
     * @param price
     * @param userId
     * @return
     */
    MaxUseableCouponOut getMaxUsableCoupon(@Param("price") BigDecimal price, @Param("userId")Integer userId);

    /**
     * 获取用户最优使用的优惠券
     * @param stationId 电站id
     * @param chargeAmount 消费金额
     * @param userId 用户id
     * @return
     */
    UserUseCoupon getFirstToUseCoupon(@Param("stationId") Integer stationId,
                               @Param("chargeAmount")BigDecimal chargeAmount,
                               @Param("userId") Integer userId);

    /**
     * 根据订单获取
     * @param orderSn
     * @param status
     * @return
     */
    UserCoupon getByOrederSn(@Param("orderSn") String orderSn, @Param("status") Integer status);

    /**
     * 根据订单更改状态
     * @param orderSn
     * @param status
     * @return
     */
    int updateStatusByOrederSn(@Param("orderSn") String orderSn, @Param("status") Integer status);

    /**
     * 将优惠券设置为过期
     * @param ids
     * @return
     */
    int setOverdueCoupon(@Param("ids")List<Integer> ids);
}