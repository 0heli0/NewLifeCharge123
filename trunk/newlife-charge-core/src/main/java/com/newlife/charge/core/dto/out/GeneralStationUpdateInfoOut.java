/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
 * 总后台 桩站详情
 */
@ApiModel
public class GeneralStationUpdateInfoOut {

    //桩站ID
    @ApiModelProperty(value = "桩站ID")
    private Integer id;

    //所属公司ID
    @ApiModelProperty(value = "所属公司ID")
    private Integer companyId;


    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String name;


    //桩站地址
    @ApiModelProperty(value = "桩站地址")
    private String address;


    //纬度
    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;
    //经度
    @ApiModelProperty(value = "经度")
    private BigDecimal lng;

    //桩站封面图片
    @ApiModelProperty(value = "桩站封面图片")
    private String coverImg;


    //管理公司
    @ApiModelProperty(value = "管理公司")
    private String managers;

    //联系方式/服务电话
    @ApiModelProperty(value = "服务电话")
    private String tel;

    //停车费描述
    @ApiModelProperty(value = "停车费说明")
    private String parkingFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(String parkingFee) {
        this.parkingFee = parkingFee;
    }
}