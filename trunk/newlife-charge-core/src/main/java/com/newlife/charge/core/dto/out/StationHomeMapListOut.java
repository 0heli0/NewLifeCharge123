/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 车主端 地图显示桩站查询
 */
@Getter
@Setter
@ApiModel(value = "地图显示桩站结果")
public class StationHomeMapListOut {


    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    @ApiModelProperty(value = "用户当前位置经度")
    private double lng;

    @ApiModelProperty(value = "用户当前位置纬度")
    private double lat;

    @ApiModelProperty(value = "距离 单位：米")
    private int distance;


}