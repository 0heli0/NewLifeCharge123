/* -------------------------------------------
 * ChargeTimeDataIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * 获取充电实时数据接收类 v1.0
 */

@Setter
@Getter
@ApiModel(value = "获取充电实时数据接收类")
public class ChargeTimeDataIn {

    @ApiModelProperty(value = "订单号,首选")
    private String orderSn;

    @ApiModelProperty(value = "充电枪id，必填")
    @NotNull(message = "充电枪id，不能为空")
    private Integer gunId;

    @ApiModelProperty(value = "车位id")
    private Integer lotId;
}