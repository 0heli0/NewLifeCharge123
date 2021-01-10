/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 充电枪 修改
 */
@Setter
@Getter
@ApiModel
public class StationClientGunUpdateIn {

    //充电枪主键ID
    @ApiModelProperty(value = "充电枪主键ID，必传")
    @NotNull(message = "充电枪主键ID不能为空")
    private Integer id;


    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID，必传")
    @NotNull(message = "请选择所属桩站")
    private Integer stationId;

    //绑定车位ID
    @ApiModelProperty(value = "绑定车位ID，必传")
    @NotNull(message = "请选择绑定车位")
    private Integer stationParkingLotId;

    //绑定充电桩ID
    @ApiModelProperty(value = "绑定充电桩ID，必传")
    @NotNull(message = "请选择绑定充电桩")
    private Integer stationClientId;

    //充电枪编号
    @ApiModelProperty(value = "充电枪编号，必传,1或者2")
    @NotNull(message = "请选择充电枪编号")
    @Pattern(regexp = RegExp.GUN_CODE, message = "充电枪编号只能为1或者2")
    private String code;

}