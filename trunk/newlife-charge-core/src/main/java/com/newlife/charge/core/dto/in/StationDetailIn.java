/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 公用详情接收类
 */
@Getter
@Setter
@ApiModel
public class StationDetailIn {

    //主键ID
    @ApiModelProperty(value = "电站id")
    @NotNull(message = "电站id不能为空")
    private Integer stationId;

    @ApiModelProperty(value = "当前定位经度,必传")
    @NotNull(message = "当前定位经度不能为空")
    private double lng;

    @ApiModelProperty(value = "当前定位纬度,必传")
    @NotNull(message = "当前定位纬度不能为空")
    private double lat;
}