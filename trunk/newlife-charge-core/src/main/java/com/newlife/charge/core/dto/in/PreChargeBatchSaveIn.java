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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


/**
 * 预充值套餐表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "批量设置预充金额设置保存接收类")
public class PreChargeBatchSaveIn {

    @Valid
    @ApiModelProperty(value = "传入设置金额的集合")
    @NotNull(message = "参数不能为空")
    @Size(min = 1,message = "必须传入金额")
    private List<PreChargeSaveIn> params;

}