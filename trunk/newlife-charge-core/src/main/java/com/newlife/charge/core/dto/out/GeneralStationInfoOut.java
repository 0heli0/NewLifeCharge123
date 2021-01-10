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
import java.util.List;


/**
 * 总后台 桩站详情
 */
@ApiModel
public class GeneralStationInfoOut {

    //桩站ID
    @ApiModelProperty(value = "桩站ID")
    private Integer id;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String name;

    //公司名称
    @ApiModelProperty(value = "绑定的公司名称")
    private String companyName;

    //桩站封面图片
    @ApiModelProperty(value = "桩站图片")
    private String coverImg;


    //充电单价(元/度)
    @ApiModelProperty(value = "基础电价(元/度)")
    private BigDecimal chargePrice;

    //服务费(元/度)
    @ApiModelProperty(value = "服务费(元/度)")
    private BigDecimal servicePrice;

    //时段电价
    @ApiModelProperty(value = "时段电价")
    private List<GeneralStationTimePriceGroupOut> stationTimePriceGroupOutList;


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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public List<GeneralStationTimePriceGroupOut> getStationTimePriceGroupOutList() {
        return stationTimePriceGroupOutList;
    }

    public void setStationTimePriceGroupOutList(List<GeneralStationTimePriceGroupOut> stationTimePriceGroupOutList) {
        this.stationTimePriceGroupOutList = stationTimePriceGroupOutList;
    }
}