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

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * 通用的价格参数接收类 v1.0
 */

@Setter
@Getter
@ApiModel(value = "通用的价格参数接收类")
public class PriceIn {


    @ApiModelProperty(value = "通用的价格参数接收类")
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
}