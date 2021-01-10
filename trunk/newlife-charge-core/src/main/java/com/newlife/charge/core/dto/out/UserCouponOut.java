/* -------------------------------------------
 * Coupon.java
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

import java.util.List;


/**
 * 用户可领取优惠券返回页面结果 v1.0
 */


@Getter
@Setter
@ApiModel("用户可领取优惠券返回页面结果")
public class UserCouponOut {

    @ApiModelProperty(value = "通用充值优惠券")
    private List<UserCouponResult> generalCharge = Lists.newArrayList();

    @ApiModelProperty(value = "通用用电优惠券")
    private List<UserCouponResult> generalElectro = Lists.newArrayList();

    @ApiModelProperty(value = "指定用电优惠券")
    private List<UserCouponResult> pointElectro = Lists.newArrayList();

}