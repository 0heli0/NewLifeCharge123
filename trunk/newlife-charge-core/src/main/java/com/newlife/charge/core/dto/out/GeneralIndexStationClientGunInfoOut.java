/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 总后台充电枪状态信息
 */
@Setter
@Getter
@ApiModel
public class GeneralIndexStationClientGunInfoOut {

    //充电枪主键ID
    @ApiModelProperty(value = "充电枪主键ID")
    private Integer id;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String stationClientCode;

    //充电枪编号
    @ApiModelProperty(value = "充电枪编号")
    private String code;

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

    //充电百分比（%）
    @ApiModelProperty(value = "充电百分比(%)")
    private String percentage;

    //终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    @ApiModelProperty(value = "终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private Integer status;

    //终端状态名称
    @ApiModelProperty(value = "终端状态名称(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private String statusName;

}