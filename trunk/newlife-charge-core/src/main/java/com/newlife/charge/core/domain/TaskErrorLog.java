/* -------------------------------------------
 * TaskErrorLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-12-07 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;


/**
 * 定时任务错误日志表 v1.0
 */


public class TaskErrorLog {

    //
    private Integer id;

    //任务执行类-全路径
    private String classPath;

    //扩展参数
    private String extendParam;

    //操作类型
    private String actionType;

    //操作状态
    private String actionStatus;

    //备注
    private String remark;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath == null ? null : classPath.trim();
    }

    public String getExtendParam() {
        return extendParam;
    }

    public void setExtendParam(String extendParam) {
        this.extendParam = extendParam == null ? null : extendParam.trim();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus == null ? null : actionStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}