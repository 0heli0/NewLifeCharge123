/* -------------------------------------------
 * StationDetail.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.StationInfo;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 桩站信息详情
 */

public class StationInfoEx extends StationInfo {

    //公司名称
    private String companyName;

    //桩站名称
    private String name;

    //桩站所在省份
    private Integer province;

    //桩站所在城市
    private Integer city;

    //桩站所在地址
    private String address;

    //桩站所在纬度
    private BigDecimal lat;

    //桩站所在经度
    private BigDecimal lng;

    //桩站封面图片
    private String coverImg;

    //桩站所在管理公司
    private String managers;

    //是否免费停车(0:否,1:是)
    private Integer freeParking;

    //停车费说明
    private String parkingFee;

    //停车场(1:地上,2:地下)
    private Integer parking;

    //营业时间
    private String businessHours;

    //联系方式
    private String tel;

    //服务星级(1 2 3 4 5星级)
    private Integer star;

    //类型(0:公共,1:专用,2:私人)-默认1
    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
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

    public Integer getFreeParking() {
        return freeParking;
    }

    public void setFreeParking(Integer freeParking) {
        this.freeParking = freeParking;
    }

    public String getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(String parkingFee) {
        this.parkingFee = parkingFee;
    }

    public Integer getParking() {
        return parking;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}