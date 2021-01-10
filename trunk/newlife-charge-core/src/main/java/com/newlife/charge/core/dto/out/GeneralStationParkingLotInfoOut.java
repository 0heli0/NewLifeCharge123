/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 总后台 桩站车位信息
 */
@Setter
@Getter
@ApiModel
public class GeneralStationParkingLotInfoOut {

    //车位Id
    @ApiModelProperty(value = "车位Id")
    private Integer id;

    //车位编号
    @ApiModelProperty(value = "车位编号")
    private String code;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;

    //绑定充电枪编号ID
    @ApiModelProperty(value = "绑定充电枪编号ID")
    private Integer stationClientGunId;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}