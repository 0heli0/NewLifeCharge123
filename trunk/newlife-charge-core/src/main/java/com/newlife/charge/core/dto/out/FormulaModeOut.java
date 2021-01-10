/* -------------------------------------------
 * UserAccount.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-30 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户账户表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "用户账户信息输出")
public class FormulaModeOut {


    @ApiModelProperty(value = "充值金额")
    private BigDecimal price;

    @ApiModelProperty(value = "充值金额-剩余金额")
    private BigDecimal givePrice;

    @ApiModelProperty(value = "充值时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date chargeTime;

    @ApiModelProperty(value = "充值时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date chargeTimestamp;

}