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
 * 总后台 公司信息分页查询
 */
@ApiModel
public class GeneralCompanyInfoPageOut {

    //公司ID
    @ApiModelProperty(value = "公司ID")
    private Integer id;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    //绑定的桩站数量
    @ApiModelProperty(value = "绑定的桩站数量")
    private Integer stationInfoCount;
    //总车位数量
    @ApiModelProperty(value = "总车位数量")
    private Integer stationParkingLotCount;
    //总充电桩数量
    @ApiModelProperty(value = "总充电桩数量")
    private Integer stationClientCount;
    //总充电枪数量
    @ApiModelProperty(value = "总充电枪数量")
    private Integer stationClientGunCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStationInfoCount() {
        return stationInfoCount;
    }

    public void setStationInfoCount(Integer stationInfoCount) {
        this.stationInfoCount = stationInfoCount;
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