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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 开始充电参数接收类
 */
@Setter
@Getter
@ApiModel
public class StopChargeIn extends ChargeParamsIn{


    @ApiModelProperty(value = "订单号")
    @NotEmpty(message = "订单号不能为空")
    private String orderSn;

}