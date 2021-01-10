/* -------------------------------------------
 * MaxUseableCouponOut.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 充值时可用的最大优惠券 v1.0
 */
@Getter
@Setter
@ApiModel("充值时可用的最大优惠券")
public class MaxUseableCouponOut {

    //主键-优惠券ID
    @ApiModelProperty(value = "优惠券主键id")
    private Integer id;

    @ApiModelProperty(value = "用户领取的优惠券表的id")
    private Integer userCouponId;

    //面额
    @ApiModelProperty(value = "面额")
    private BigDecimal price;

}