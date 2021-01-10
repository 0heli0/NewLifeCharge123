/* -------------------------------------------
 * StationTimePrice.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 用户端充电站详情 v1.0
 */
@Getter
@Setter
public class ClientStationDetailEx {

    // "电站id"
    private Integer stationId;

    // "电站名称"
    private String stationName;

    // "基础电价"
    private BigDecimal chargePrice;

    // "最终电价"
    private BigDecimal price;

    // "服务费"
    private BigDecimal servicePrice;

    // "停车场收费说明"
    private String parkingFee;

    // "桩站现场管理公司"
    private String managers;

    // "联系方式/电话"
    private String tel;

    // "涨幅(%):+涨-减"
    private Integer increase;

    // "阶梯电价数"
    private int increaseCount;


}