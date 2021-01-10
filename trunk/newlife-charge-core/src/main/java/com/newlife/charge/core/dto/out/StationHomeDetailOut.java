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
 * 车主端 地图显示桩站查询
 */
@Getter
@Setter
@ApiModel(value = "地图显示桩站详情结果")
public class StationHomeDetailOut {


    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    @ApiModelProperty(value = "充电站名称")
    private String name;

    @ApiModelProperty(value = "充电站详细地址")
    private String address;

    // 桩站定位经度
    @ApiModelProperty(value = "桩站定位经度")
    private double lng;

    // 桩站定位纬度
    @ApiModelProperty(value = "桩站定位纬度")
    private double lat;

    @ApiModelProperty(value = "电价")
    private BigDecimal price;

    @ApiModelProperty(value = "距离 单位（米）")
    private Integer distance;

    @ApiModelProperty(value = "快充桩总数")
    private Integer fastChargeTotal;

    @ApiModelProperty(value = "快充桩剩余数量")
    private Integer fastChargeLeft;

    @ApiModelProperty(value = "慢充桩总数")
    private Integer trickleChargeTotal;

    @ApiModelProperty(value = "慢充桩剩余数量")
    private Integer trickleChargeLeft;

    @ApiModelProperty(value = "是否有优惠券可以使用(0:没有,1:有)")
    private Integer isCoupon;

    @ApiModelProperty(value = "是否降价中(0:没有,1:有),预留,目前版本默认为0")
    private Integer isReduce;


}