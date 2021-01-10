/* -------------------------------------------
 * StationCouponCount.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 桩站可用优惠券数量 v1.0
 */

@Getter
@Setter
public class StationCouponCount {


    // 所属桩站ID
    private Integer stationId;

    // 可领优惠券总数
    private Integer total;

}