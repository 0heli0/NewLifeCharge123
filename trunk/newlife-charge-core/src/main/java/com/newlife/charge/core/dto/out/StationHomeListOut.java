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

import java.math.BigDecimal;

/**
 * 车主端 搜一搜显示桩站列表
 */
@Getter
@Setter
@ApiModel(value = "搜一搜显示桩站列表")
public class StationHomeListOut {


    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    @ApiModelProperty(value = "桩站头像图片")
    private String coverImg;

    @ApiModelProperty(value = "用户当前位置经度,供前端传回后端时使用")
    private double lng;

    @ApiModelProperty(value = "用户当前位置纬度,供前端传回后端时使用")
    private double lat;

    @ApiModelProperty(value = "桩站名称")
    private String name;

    @ApiModelProperty(value = "距离,单位:米")
    private int distance;

    @ApiModelProperty(value = "桩站地址")
    private String address;

    @ApiModelProperty(value = "充电枪总数")
    private Integer chargeGun;

    @ApiModelProperty(value = "空闲充电枪")
    private Integer freeGun;

    @ApiModelProperty(value = "电价")
    private BigDecimal price;

    @ApiModelProperty(value = "基本电价")
    private BigDecimal chargePrice;

    @ApiModelProperty(value = "服务价")
    private BigDecimal servicePrice;

    @ApiModelProperty(value = "是否有可用优惠券(0:没有,1有)")
    private int isCoupon;

    @ApiModelProperty(value = "是否降价(0:没有,1有)")
    private int isSale;


}