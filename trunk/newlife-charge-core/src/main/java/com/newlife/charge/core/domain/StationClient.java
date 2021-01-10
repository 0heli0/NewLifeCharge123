/* -------------------------------------------
 * StationClient.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-29 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 充电桩表 v1.0
 */


public class StationClient {

    //主键-充电桩ID
    private Integer id;

    //所属桩站ID
    private Integer stationId;

    //充电桩编号
    private String code;

    //充电桩终端号
    private String terminalNo;

    //品牌
    private String brand;

    //单双枪类型(1:单枪，2:双枪)
    private Integer gunType;

    //充电方式(1:直流快充,2:交流快充,3:交流慢充)
    private Integer chargeType;

    //充电接口(1:国标2011,2:国标2015,3:特斯拉)
    private Integer chargeInterfaceType;

    //最低功率(kW)
    private Integer powerMin;

    //最高功率(kW)
    private Integer powerMax;

    //最低电压(V)
    private Integer voltageMin;

    //最高电压(V)
    private Integer voltageMax;

    //辅源类型
    private String auxiliaryType;

    //创建时间
    private Date createTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo == null ? null : terminalNo.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public Integer getGunType() {
        return gunType;
    }

    public void setGunType(Integer gunType) {
        this.gunType = gunType;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getChargeInterfaceType() {
        return chargeInterfaceType;
    }

    public void setChargeInterfaceType(Integer chargeInterfaceType) {
        this.chargeInterfaceType = chargeInterfaceType;
    }

    public Integer getPowerMin() {
        return powerMin;
    }

    public void setPowerMin(Integer powerMin) {
        this.powerMin = powerMin;
    }

    public Integer getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(Integer powerMax) {
        this.powerMax = powerMax;
    }

    public Integer getVoltageMin() {
        return voltageMin;
    }

    public void setVoltageMin(Integer voltageMin) {
        this.voltageMin = voltageMin;
    }

    public Integer getVoltageMax() {
        return voltageMax;
    }

    public void setVoltageMax(Integer voltageMax) {
        this.voltageMax = voltageMax;
    }

    public String getAuxiliaryType() {
        return auxiliaryType;
    }

    public void setAuxiliaryType(String auxiliaryType) {
        this.auxiliaryType = auxiliaryType == null ? null : auxiliaryType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}