/* -------------------------------------------
 * ClientStationTimePriceEx.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 充电站阶梯电价输出类 v1.0
 */
@Getter
@Setter
@ApiModel(value = "充电站阶梯电价输出类")
public class ClientStationTimePriceOut {

    @ApiModelProperty(value = "电站名称")
    private String name;

    @ApiModelProperty(value = "基础电价")
    private BigDecimal chargePrice;

    @ApiModelProperty(value = "各时段实时电价")
    private BigDecimal price;

    @ApiModelProperty(value = "时段")
    private Integer timeNo;

    @ApiModelProperty(value = "涨幅(%):+涨-减")
    private Integer increase;

    @ApiModelProperty(value = "每个时区时间集合")
    private List<String> times = Lists.newArrayList();

}