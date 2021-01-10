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

import javax.validation.constraints.Size;


/**
 * 预充值套餐表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "预充值设置批量删除新接收类")
public class PreChargeDeleteIdsIn {

    @ApiModelProperty(required = true, value = "需要删除的反馈id数组,必填,长度不能为0,元素格式为数字")
    @Size(min = 1,message = "传入id参数不能为空")
    private int[] ids;

}