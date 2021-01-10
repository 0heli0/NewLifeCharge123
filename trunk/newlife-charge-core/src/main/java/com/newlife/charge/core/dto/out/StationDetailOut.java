/* -------------------------------------------
 * StationDetail.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 桩站详情表 v1.0
 */
@Getter
@Setter
@ApiModel(value = "桩站详情")
public class StationDetailOut {

    //主键-桩站详情ID
    @ApiModelProperty(value = "桩站详情ID")
    private Integer id;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String name;

    //桩站所在地址
    @ApiModelProperty(value = "桩站所在地址")
    private String address;


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }


}