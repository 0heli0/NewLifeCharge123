/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 充电桩绑定的充电枪数量统计
 */
@Getter
@Setter
@ApiModel
public class StationClientGunNumOut {

    //充电桩ID
    @ApiModelProperty(value = "充电桩ID")
    private Integer stationClientId;

    //绑定的枪的数量
    @ApiModelProperty(value = "绑定的枪的数量")
    private Integer num;


}