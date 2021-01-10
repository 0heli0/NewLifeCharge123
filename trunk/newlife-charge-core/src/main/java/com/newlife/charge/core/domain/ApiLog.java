/* -------------------------------------------
 * ApiLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 接口日志表 v1.0
 */


public class ApiLog {

    //主键-接口日志id
    private Integer id;

    //服务名
    private String server;

    //主机
    private String host;

    //controller
    private String controller;

    //动作
    private String action;

    //IMEI号
    private String imei;

    //设备号
    private String device;

    //UserAgent
    private String userAgent;

    //Hash值
    private String hash;

    //Token
    private String token;

    //验证结果
    private String validationResult;

    //IP地址
    private String ipAddress;

    //MAC地址
    private String macAddress;

    //系统版本
    private String osVersion;

    //创建时间
    private Date createTime;

    //请求和参数
    private String pathQuery;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server == null ? null : server.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller == null ? null : controller.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device == null ? null : device.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult == null ? null : validationResult.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress == null ? null : macAddress.trim();
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion == null ? null : osVersion.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPathQuery() {
        return pathQuery;
    }

    public void setPathQuery(String pathQuery) {
        this.pathQuery = pathQuery == null ? null : pathQuery.trim();
    }
}