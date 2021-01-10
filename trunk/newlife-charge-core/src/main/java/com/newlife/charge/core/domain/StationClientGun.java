/* -------------------------------------------
 * StationClientGun.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-29 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;


/**
 * 充电枪表 v1.0
 */


public class StationClientGun {

    //主键-充电枪ID
    private Integer id;

    //所属桩站ID
    private Integer stationId;

    //绑定车位ID
    private Integer stationParkingLotId;

    //绑定充电桩ID
    private Integer stationClientId;

    //充电枪编号
    private String code;

    //充电枪号（与编号一致）
    private String gunNo;

    //终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    private Integer status;

    //充电时电压(V)
    private String voltage;
    //充电时电流(A)
    private String electric;
    //充电时功率(KW)
    private String power;
    //充电时温度(℃)
    private String temperature;
    //充电百分比(%)
    private String percentage;

    //充电时间(秒)
    private String chargeTime;
    //充电电能(KWH)
    private String chargeEnergy;
    //充电金额（元）
    private String chargeAmount;

    //创建时间
    private Date createTime;

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getChargeEnergy() {
        return chargeEnergy;
    }

    public void setChargeEnergy(String chargeEnergy) {
        this.chargeEnergy = chargeEnergy;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getGunNo() {
        return gunNo;
    }

    public void setGunNo(String gunNo) {
        this.gunNo = gunNo == null ? null : gunNo.trim();
    }

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

    public Integer getStationParkingLotId() {
        return stationParkingLotId;
    }

    public void setStationParkingLotId(Integer stationParkingLotId) {
        this.stationParkingLotId = stationParkingLotId;
    }

    public Integer getStationClientId() {
        return stationClientId;
    }

    public void setStationClientId(Integer stationClientId) {
        this.stationClientId = stationClientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage == null ? null : voltage.trim();
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric == null ? null : electric.trim();
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power == null ? null : power.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage == null ? null : percentage.trim();
    }
}