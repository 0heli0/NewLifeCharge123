/* -------------------------------------------
 * OperationLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 操作日志表 v1.0
 */


public class OperationLog {

    //主键-操作日志id
    private Integer id;

    //用户ID
    private Integer userId;

    //用户类型(1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户)
    private Integer userType;


    //桩站id
    private Integer stationId;

    //登录账号(可能与手机号码一致)
    private String loginName;

    //操作类型:操作类型
    private Integer operationType;

    //操作动作
    private String action;

    //操作模块
    private String operationMoudle;

    //请求的网址
    private String requestUrl;

    //IP地址
    private String ip;

    //创建时间
    private Date createTime;

    //操作内容
    private String operationText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getOperationMoudle() {
        return operationMoudle;
    }

    public void setOperationMoudle(String operationMoudle) {
        this.operationMoudle = operationMoudle == null ? null : operationMoudle.trim();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperationText() {
        return operationText;
    }

    public void setOperationText(String operationText) {
        this.operationText = operationText == null ? null : operationText.trim();
    }
}