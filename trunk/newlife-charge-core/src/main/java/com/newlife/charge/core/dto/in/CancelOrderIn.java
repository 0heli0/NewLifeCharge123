/* -------------------------------------------
 * PriceIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * 取消订单 v1.0
 */

@Setter
@Getter
@ApiModel(value = "取消订单接收类")
public class CancelOrderIn {


    @ApiModelProperty(value = "订单编号，必填")
    @NotEmpty(message = "订单编号不能为空")
    private String orderSn;

    @ApiModelProperty(value = "用户领取的优惠券表中的id，当没有使用优惠券的时候传0，必填")
    @NotNull(message = "用户优惠券id不能为空")
    private Integer userCouponId;
}