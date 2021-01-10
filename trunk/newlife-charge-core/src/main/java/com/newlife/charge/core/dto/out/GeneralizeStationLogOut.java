/* -------------------------------------------
 * GeneralizeStationLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 推广建站记录
 */

@Getter
@Setter
@ApiModel(value = "操作日志")
public class GeneralizeStationLogOut {

    //主键-推广建站记录id
    @ApiModelProperty(value = "主键-推广建站记录id")
    private Integer id;

    //推广人电话
    @ApiModelProperty(value = "推广人电话")
    private String mobile;

    //推广人昵称
    @ApiModelProperty(value = "推广人昵称")
    private String name;

    //预计建站时间
    @ApiModelProperty(value = "预计建站时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date buildTime;

    //电站地址
    @ApiModelProperty(value = "电站地址")
    private String stationAddress;

    //电站描述
    @ApiModelProperty(value = "电站描述")
    private String stationDescript;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}