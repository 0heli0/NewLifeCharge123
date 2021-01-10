/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 充电枪 详情
 */
@Getter
@Setter
@ApiModel
public class StationClientGunInfoOut {

    //充电枪主键ID
    @ApiModelProperty(value = "充电枪主键ID")
    private Integer id;

    //充电枪编号
    @ApiModelProperty(value = "充电枪编号")
    private String code;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;

    //所属桩站名称
    @ApiModelProperty(value = "所属桩站名称")
    private String stationName;

    //车位ID
    @ApiModelProperty(value = "车位ID")
    private Integer stationParkingLotId;

    //车位编号
    @ApiModelProperty(value = "车位编号")
    private String stationParkingLotCode;


    //充电桩ID
    @ApiModelProperty(value = "充电桩ID")
    private Integer stationClientId;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String stationClientCode;

    //终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    @ApiModelProperty(value = "终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private Integer status;

    //终端状态名称
    @ApiModelProperty(value = "终端状态名称(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private String  statusName;

    //创建时间
    @ApiModelProperty(value = "添加时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //电压
    @ApiModelProperty(value = "电压")
    private String voltage;
    //电流
    @ApiModelProperty(value = "电流")
    private String electric;
    //功率
    @ApiModelProperty(value = "功率")
    private String power;
    //温度
    @ApiModelProperty(value = "温度")
    private String temperature;
    //充电百分比
    @ApiModelProperty(value = "充电百分比")
    private String percentage;

}