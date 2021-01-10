/* -------------------------------------------
 * PreCharge.java
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
 * 预充值套餐表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "预充值设置删除接收类")
public class PreChargeDeleteIn {

    @ApiModelProperty(value = "需要单条删除的主键Id")
    @NotNull(message = "需要单条删除的主键Id不能空")
    private Integer id;

}