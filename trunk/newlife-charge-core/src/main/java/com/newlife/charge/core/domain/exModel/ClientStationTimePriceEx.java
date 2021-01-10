/* -------------------------------------------
 * ClientStationTimePriceEx.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;


/**
 * 充电站阶梯电价输出类 v1.0
 */
@Getter
@Setter
// 充电站阶梯电价扩展接收类
public class ClientStationTimePriceEx {

    // 电站名称
    private String name;

    // 基础电价
    private BigDecimal chargePrice;

    // 服务费
    private BigDecimal servicePrice;

    // 实时电价
    private BigDecimal price;

    // 时段
    private Integer timeNo;

    // 时段开始时间
    private Time timeBegin;

    // 时段结束时间
    private Time timeEnd;

    // 涨幅(%):+涨-减
    private Integer increase;

}