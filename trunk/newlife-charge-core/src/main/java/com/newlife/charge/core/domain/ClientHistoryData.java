/* -------------------------------------------
 * ClientHistoryData.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-05-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 充电桩充电历史数据（即硬件充电账单）表 v1.0
 */


public class ClientHistoryData {

    //主键-新建充电桩历史数据ID
    private Integer id;

    //充电桩编号
    private String stationClientCode;

    //充电枪编号
    private String gunCode;

    //充电方式(0:立即充电 1：预约充电)
    private Integer chargeWay;

    //充电模式(0.自动充电 1.金额模式 2.时间模式 3.电量模式)
    private Integer chargeMode;

    //充电卡类型(1.用户卡 81.员工卡 97.充值卡 112.在线卡 113.APP用户 120.VIN码)
    private Integer chargeCardType;

    //充电卡号(用户手机号)
    private String chargeCardNo;

    //车辆VIN码
    private String carVin;

    //充电前余额/元，两位小数
    private String babe;

    //充电电压，一位小数
    private String chargeVoltage;

    //充电电流，两位小数
    private String chargeElectricity;

    //充电时间/秒
    private String chargeTime;

    //充电金额/元，两位小数
    private String chargeAmount;

    //充电电能,两位小数
    private String chargeEnergy;

    //充电开始电能,两位小数
    private String chargeEnergyStart;

    //充电结束电能,两位小数
    private String chargeEnergyEnd;

    //剩余时间,只限直流
    private String leftTime;

    //当前SOC(充电百分比/%),只限直流
    private String currentSoc;

    //是否上传主站(0.没有上传 1.上传)
    private Integer uploadMaster;

    //是否付费( 0.没有正常付费 1.已经付费)
    private Integer pay;

    //充电终止原因(0.正常接收 1.异常结束)
    private Integer chargeEndReason;

    //充电开始时间
    private Date chargeTimeStart;

    //充电结束时间
    private Date chargeTimeEnd;

    //记录流水号--启动充电时下发的交易号/订单ID（transactionNo)
    private Integer recordNo;

    //记录存储序号
    private Integer recordStorageNo;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationClientCode() {
        return stationClientCode;
    }

    public void setStationClientCode(String stationClientCode) {
        this.stationClientCode = stationClientCode == null ? null : stationClientCode.trim();
    }

    public String getGunCode() {
        return gunCode;
    }

    public void setGunCode(String gunCode) {
        this.gunCode = gunCode == null ? null : gunCode.trim();
    }

    public Integer getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(Integer chargeWay) {
        this.chargeWay = chargeWay;
    }

    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }

    public Integer getChargeCardType() {
        return chargeCardType;
    }

    public void setChargeCardType(Integer chargeCardType) {
        this.chargeCardType = chargeCardType;
    }

    public String getChargeCardNo() {
        return chargeCardNo;
    }

    public void setChargeCardNo(String chargeCardNo) {
        this.chargeCardNo = chargeCardNo == null ? null : chargeCardNo.trim();
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin == null ? null : carVin.trim();
    }

    public String getBabe() {
        return babe;
    }

    public void setBabe(String babe) {
        this.babe = babe == null ? null : babe.trim();
    }

    public String getChargeVoltage() {
        return chargeVoltage;
    }

    public void setChargeVoltage(String chargeVoltage) {
        this.chargeVoltage = chargeVoltage == null ? null : chargeVoltage.trim();
    }

    public String getChargeElectricity() {
        return chargeElectricity;
    }

    public void setChargeElectricity(String chargeElectricity) {
        this.chargeElectricity = chargeElectricity == null ? null : chargeElectricity.trim();
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime == null ? null : chargeTime.trim();
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount == null ? null : chargeAmount.trim();
    }

    public String getChargeEnergy() {
        return chargeEnergy;
    }

    public void setChargeEnergy(String chargeEnergy) {
        this.chargeEnergy = chargeEnergy == null ? null : chargeEnergy.trim();
    }

    public String getChargeEnergyStart() {
        return chargeEnergyStart;
    }

    public void setChargeEnergyStart(String chargeEnergyStart) {
        this.chargeEnergyStart = chargeEnergyStart == null ? null : chargeEnergyStart.trim();
    }

    public String getChargeEnergyEnd() {
        return chargeEnergyEnd;
    }

    public void setChargeEnergyEnd(String chargeEnergyEnd) {
        this.chargeEnergyEnd = chargeEnergyEnd == null ? null : chargeEnergyEnd.trim();
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime == null ? null : leftTime.trim();
    }

    public String getCurrentSoc() {
        return currentSoc;
    }

    public void setCurrentSoc(String currentSoc) {
        this.currentSoc = currentSoc == null ? null : currentSoc.trim();
    }

    public Integer getUploadMaster() {
        return uploadMaster;
    }

    public void setUploadMaster(Integer uploadMaster) {
        this.uploadMaster = uploadMaster;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getChargeEndReason() {
        return chargeEndReason;
    }

    public void setChargeEndReason(Integer chargeEndReason) {
        this.chargeEndReason = chargeEndReason;
    }

    public Date getChargeTimeStart() {
        return chargeTimeStart;
    }

    public void setChargeTimeStart(Date chargeTimeStart) {
        this.chargeTimeStart = chargeTimeStart;
    }

    public Date getChargeTimeEnd() {
        return chargeTimeEnd;
    }

    public void setChargeTimeEnd(Date chargeTimeEnd) {
        this.chargeTimeEnd = chargeTimeEnd;
    }

    public Integer getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Integer recordNo) {
        this.recordNo = recordNo;
    }

    public Integer getRecordStorageNo() {
        return recordStorageNo;
    }

    public void setRecordStorageNo(Integer recordStorageNo) {
        this.recordStorageNo = recordStorageNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}