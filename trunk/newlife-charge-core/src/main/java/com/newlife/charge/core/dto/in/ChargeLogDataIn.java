/* -------------------------------------------
 * ChargeLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


/**
 * 车主用户充电记录表 查询接收类 v1.0
 */


@Getter
@Setter
@ApiModel(value = "查询充电结果接收类")
public class ChargeLogDataIn {

    @ApiModelProperty(value = "充电记录的订单号，不传的话，默认查改用户最近的一条记录")
    private String orderSn;
}