/* -------------------------------------------
 * CreateOrderIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 微信支付下单接收参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "微信充值下单接收参数")
public class CreateOrderIn {

    @ApiModelProperty(value = "优惠券ID")
    private Integer couId;

    @ApiModelProperty(value = "用户使用优惠券ID")
    private Integer cuId;

    @ApiModelProperty(value = "预充值ID,必填")
    @NotNull(message = "预充值ID,不能为空")
    private Integer preId;

    @ApiModelProperty(value = "支付方式（1：微信 2：支付宝）,必填")
    @NotNull(message = "支付方式,不能为空")
    private Integer payType;

}