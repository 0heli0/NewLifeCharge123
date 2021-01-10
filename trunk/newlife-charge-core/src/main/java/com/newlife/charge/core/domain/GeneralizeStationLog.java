/* -------------------------------------------
 * GeneralizeStationLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 推广建站记录表 v1.0
 */


public class GeneralizeStationLog {

    //主键-推广建站记录id
    private Integer id;

    //推广人电话
    private String mobile;

    //推广人昵称
    private String name;

    //预计建站时间
    private Date buildTime;

    //电站地址
    private String stationAddress;

    //电站描述
    private String stationDescript;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress == null ? null : stationAddress.trim();
    }

    public String getStationDescript() {
        return stationDescript;
    }

    public void setStationDescript(String stationDescript) {
        this.stationDescript = stationDescript == null ? null : stationDescript.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}