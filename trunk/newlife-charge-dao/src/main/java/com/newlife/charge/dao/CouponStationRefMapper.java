/* -------------------------------------------
 * CouponStationRefMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-26 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.newlife.charge.core.domain.CouponStationRef;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponStationRefMapper extends CrudRepository<CouponStationRef> {

    /**
     * 批量添加
     * @param list
     */
    void batchInsert(@Param("list") List<CouponStationRef> list);

    /**
     * 通过优惠券id获取
     * @param couponId
     * @return
     */
    List<CouponStationRef> getByCouponId(@Param("couponId")Integer couponId);

    /**
     * 通过优惠券id获取
     * @param ids
     * @return
     */
    List<CouponStationRef> getByCouponIds(@Param("ids")int[] ids);


}