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
 * 总后台 充电桩故障日志分页查询
 */
@Setter
@Getter
@ApiModel
public class ClientAlarmDataPageOut {

    //主键ID
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String stationClientCode;

    //充电枪编号
    @ApiModelProperty(value = "充电枪编号")
    private String gunCode;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //桩站公司名称
    @ApiModelProperty(value = "桩站公司名称")
    private String companyName;

    //告警点中文解释
    @ApiModelProperty(value = "告警点")
    private String alarmPointName;

    //告警点中文解释
    @ApiModelProperty(value = "告警原因")
    private String alarmReasonName;

    //告警开始时间
    @ApiModelProperty(value = "告警开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmTimeStart;

    //告警结束时间
    @ApiModelProperty(value = "告警结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmTimeEnd;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}