/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 车主端 地图显示桩站查询
 */
@Getter
@Setter
@ApiModel(value = "地图显示桩站信息接收类")
public class StationHomeListIn {

    @ApiModelProperty(value = "用户当前位置经度")
    @NotNull(message = "用户当前位置经度,不能为空")
    private double lng;

    @ApiModelProperty(value = "用户当前位置纬度")
    @NotNull(message = "用户当前位置纬度,不能为空")
    private double lat;

    @ApiModelProperty(value = "查找范围,单位:米")
    @NotNull(message = "查找范围,不能为空")
    private Integer distance;

}