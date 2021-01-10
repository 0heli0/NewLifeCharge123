/* -------------------------------------------
 * StationUserRef.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * 桩站用户关联表 v1.0
 */


public class StationUserRefOut {

    @ApiModelProperty(value = "主键-桩站用户关联id")
    private Integer id;

    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    @ApiModelProperty(value = "用户id(桩站用户)")
    private Integer userId;

    @ApiModelProperty(value = "状态(1:启用，2停用)")
    private Integer status;

    @ApiModelProperty(value = "公司Id")
    private Integer companyInfoId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCompanyInfoId() {
        return companyInfoId;
    }

    public void setCompanyInfoId(Integer companyInfoId) {
        this.companyInfoId = companyInfoId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}