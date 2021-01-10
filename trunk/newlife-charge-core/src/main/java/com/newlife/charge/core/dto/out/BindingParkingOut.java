/* -------------------------------------------
 * BindingParkingOut.java
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
 * 桩站车位信息 v1.0
 */
@Getter
@Setter
@ApiModel(value = "车主端-选定车位是否成功")
public class BindingParkingOut {

    @ApiModelProperty(value = "充电枪状态(0：已经被占用,1：可用)")
    private int status;

    @ApiModelProperty(value = "是否需要充值(0：需要充值,1：不需要)")
    private int isNeedCharge;

    @ApiModelProperty(value = "是否绑定了其他车位（0:没有绑定，1：已经绑定了其他车位，请先解绑）")
    private int isBindOther;

}