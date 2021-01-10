/* -------------------------------------------
 * WeiRefundIn.java
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
import java.math.BigDecimal;

/**
 * 用户微信退款接收参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "用户微信退款接收参数")
public class WeiRefundIn {

    @ApiModelProperty(value = "商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)")
    @NotNull(message = "商户订单号,不能为空")
    private String orderSn;

    @ApiModelProperty(value = "商户appid下，某用户的openid")
    @NotNull(message = "商户appid下,不能为空")
    private String openid;

    @ApiModelProperty(value = "企业付款金额，单位为元")
    @NotNull(message = "企业付款金额,不能为空")
    private BigDecimal amount;


}