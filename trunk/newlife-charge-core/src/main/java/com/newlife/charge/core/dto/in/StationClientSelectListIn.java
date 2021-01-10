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
 * 充电桩下拉框入参
 */
@Getter
@Setter
@ApiModel
public class StationClientSelectListIn {

    //桩站ID
    @ApiModelProperty(value = "主键ID，必传")
    @NotNull(message = "桩站ID不能为空")
    private Integer stationId;

    //充电桩ID
    @ApiModelProperty(value = "充电桩ID，需要特别将此数据放在结果中")
    private Integer stationClientId;

}