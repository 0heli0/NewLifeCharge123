/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 总后台 桩站信息分页查询
 */
@ApiModel
public class GeneralStationInfoPageOut {

    //桩站ID
    @ApiModelProperty(value = "桩站ID")
    private Integer id;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String name;

    //桩站地址
    @ApiModelProperty(value = "桩站详细地址")
    private String address;

    //绑定的桩站公司名称
    @ApiModelProperty(value = "绑定的桩站公司")
    private String companyName;

    //车位数量
    @ApiModelProperty(value = "车位数量")
    private Integer stationParkingLotCount;

    //充电桩数量
    @ApiModelProperty(value = "充电桩数量")
    private Integer stationClientCount;

    //充电枪数量
    @ApiModelProperty(value = "充电枪数量")
    private Integer stationClientGunCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStationParkingLotCount() {
        return stationParkingLotCount;
    }

    public void setStationParkingLotCount(Integer stationParkingLotCount) {
        this.stationParkingLotCount = stationParkingLotCount;
    }

    public Integer getStationClientCount() {
        return stationClientCount;
    }

    public void setStationClientCount(Integer stationClientCount) {
        this.stationClientCount = stationClientCount;
    }

    public Integer getStationClientGunCount() {
        return stationClientGunCount;
    }

    public void setStationClientGunCount(Integer stationClientGunCount) {
        this.stationClientGunCount = stationClientGunCount;
    }
}