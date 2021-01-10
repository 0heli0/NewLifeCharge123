package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.CouponMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.Coupon;
import com.newlife.charge.core.dto.in.CouponQueryIn;
import com.newlife.charge.core.dto.in.CouponStationQueryIn;
import com.newlife.charge.core.dto.out.CouponOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CouponMapper extends CrudRepository<Coupon> {

    /**
     * 分页查找
     * @param queryIn
     * @return
     */
    Page<CouponOut> page(@Param("query") CouponQueryIn queryIn);

    /**
     * 获取过期优惠券的id
     * @return
     */
    List<Integer> getOverdueCoupon();

    /**
     * 通过id将过期数据修改成过期状态
     * @param list
     * @return
     */
    int setOverdueCoupon(@Param("ids")List<Integer> list);

    /**
     * 详情
     * @param id
     * @return
     */
    CouponOut info(@Param("id") Integer id);

    /**
     * 下架
     * @param id
     * @return
     */
    int out(@Param("id") Integer id);

    /**
     * 根据id删除
     * @param id
     * @returns
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 桩站 分页查找
     * @param queryIn
     * @return
     */
    Page<CouponOut> pageList(CouponStationQueryIn queryIn);

    /**
     * 根据面额获取数据
     * @param coupon
     * @return
     */
    List<Coupon> getByParams(Coupon coupon);

    /**
     * @Description: 过滤 面额和门槛相同的优惠劵
     * @Author: Linzq
     * @CreateDate:  2019/5/23 0023 14:26
     */
    Coupon getByQuery(CouponQueryIn couponQueryIn);

    /**
     * 获取优惠券指定桩站名称
     * @param id
     * @return
     */
    String getStationNames(Integer id);

    /**
     * 查询用户优惠券中已过期，但未修改状态的，并修改
     * @return
     */
    List<Integer> getOverdueUserCoupon();
}