/* -------------------------------------------
 * UnifiedOrderQueryIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 微信支付统一下接收参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "微信支付统一下接收参数")
public class UnifiedOrderQueryIn {

    @ApiModelProperty(value = "订单号")
    @NotEmpty(message = "订单号,不能为空")
    private String orderSn;

    @ApiModelProperty(value = "支付下单的OpenId,必填")
    @NotNull(message = "支付下单的OpenId,不能为空")
    private String OpenId;

}