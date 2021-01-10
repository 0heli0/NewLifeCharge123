/* -------------------------------------------
 * ChargeParamsIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 开始充电参数接收类
 */
@Setter
@Getter
@ApiModel
public class ChargeParamsIn {

    @ApiModelProperty(value = "绑定的车位的id")
    @NotNull(message = "绑定的车位的id，不能为空")
    private Integer id;

    @ApiModelProperty(value = "当前充电枪Id")
    @NotNull(message = "当前充电枪Id，不能为空")
    private Integer gunId;

    @ApiModelProperty(value = "确认充电状态(0：未确认,1：开始充电, 2:停止充电)")
    @NotNull(message = "确认充电状态，不能为空")
    private Integer status;

    @ApiModelProperty(value = "订单号，确认充电时不传")
    private String orderSn;

}