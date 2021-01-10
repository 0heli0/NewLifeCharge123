/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * RPC充电桩编号和枪编号
 */
@Setter
@Getter
@ApiModel
public class RpcChargePileInfoIn {

    //充电桩终端号/充电桩编号
    @ApiModelProperty(value = "充电桩终端号，必传,最长8位")
    @NotBlank(message = "充电桩终端号不能为空")
    @Length(max = 8, message = "充电桩终端号不能为空长度最长8位")
    private String stationClientTerminalNo;

    //充电枪编号
    @ApiModelProperty(value = "充电枪号，必传")
    @NotBlank(message = "充电枪号不能为空")
    private String gunNo;

}