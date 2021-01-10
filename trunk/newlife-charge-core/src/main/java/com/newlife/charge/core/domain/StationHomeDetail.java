/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 车主端 地图显示桩站查询
 */
@Getter
@Setter
public class StationHomeDetail {

    // 充电站名称
    private String name;

    // 充电站详细地址
    private String address;

    // 电价
    private BigDecimal chargePrice;

    // 服务费
    private BigDecimal servicePrice;

    // 涨价幅度
    private Integer increase;

    // 桩站定位经度
    private double lng;

    // 桩站定位纬度
    private double lat;

}