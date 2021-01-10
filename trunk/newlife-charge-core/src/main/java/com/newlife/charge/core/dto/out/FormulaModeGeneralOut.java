/* -------------------------------------------
 * FormulaModeGeneralOut.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-30 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


/**
 * 用户账户总信息输出表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "用户账户总信息输出")
public class FormulaModeGeneralOut {

    @ApiModelProperty("用户剩余金额")
    private BigDecimal balance;

    @ApiModelProperty("用户优惠剩余金额")
    private BigDecimal givePrice;

    @ApiModelProperty("用户可退金额")
    private BigDecimal chargePrice;

    @ApiModelProperty(value = "剩余可退款余额列表")
    private List<FormulaModeOut> formulaModeOut = Lists.newArrayList();

}