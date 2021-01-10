/* -------------------------------------------
 * ClientAlarmData.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-06-05 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 充电桩告警记录表 v1.0
 */


public class ClientAlarmData {

    //主键-充电桩告警记录ID
    private Integer id;

    //充电桩编号
    private String stationClientCode;

    //充电枪编号
    private String gunCode;

    //告警点
    private String alarmPoint;

    //告警点中文解释
    private String alarmPointName;

    //告警原因
    private String alarmReason;

    //告警点中文解释
    private String alarmReasonName;

    //告警开始时间
    private Date alarmTimeStart;

    //告警结束时间
    private Date alarmTimeEnd;

    //告警持续时间/秒
    private String alarmTime;

    //是否影响充电(0.无影响 1.有影响)
    private Integer affectCharge;

    //是否上传主站(0.没有上传 1.上传)
    private Integer uploadMaster;

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

    public String getAlarmPoint() {
        return alarmPoint;
    }

    public void setAlarmPoint(String alarmPoint) {
        this.alarmPoint = alarmPoint == null ? null : alarmPoint.trim();
    }

    public String getAlarmPointName() {
        return alarmPointName;
    }

    public void setAlarmPointName(String alarmPointName) {
        this.alarmPointName = alarmPointName == null ? null : alarmPointName.trim();
    }

    public String getAlarmReason() {
        return alarmReason;
    }

    public void setAlarmReason(String alarmReason) {
        this.alarmReason = alarmReason == null ? null : alarmReason.trim();
    }

    public String getAlarmReasonName() {
        return alarmReasonName;
    }

    public void setAlarmReasonName(String alarmReasonName) {
        this.alarmReasonName = alarmReasonName == null ? null : alarmReasonName.trim();
    }

    public Date getAlarmTimeStart() {
        return alarmTimeStart;
    }

    public void setAlarmTimeStart(Date alarmTimeStart) {
        this.alarmTimeStart = alarmTimeStart;
    }

    public Date getAlarmTimeEnd() {
        return alarmTimeEnd;
    }

    public void setAlarmTimeEnd(Date alarmTimeEnd) {
        this.alarmTimeEnd = alarmTimeEnd;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime == null ? null : alarmTime.trim();
    }

    public Integer getAffectCharge() {
        return affectCharge;
    }

    public void setAffectCharge(Integer affectCharge) {
        this.affectCharge = affectCharge;
    }

    public Integer getUploadMaster() {
        return uploadMaster;
    }

    public void setUploadMaster(Integer uploadMaster) {
        this.uploadMaster = uploadMaster;
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