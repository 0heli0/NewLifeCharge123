/* -------------------------------------------
 * stageData.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-05-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 十个时段充电电量和充电金额表 v1.0
 */


public class StageData {

    //主键-新建充电桩历史数据ID
    private Integer id;

    //充电桩充电历史数据主键ID
    private Integer clientHistoryId;

    //时段序号
    private Integer time;

    //充电金额/元，两位小数
    private String balance;

    //充电电能，两位小数
    private String power;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientHistoryId() {
        return clientHistoryId;
    }

    public void setClientHistoryId(Integer clientHistoryId) {
        this.clientHistoryId = clientHistoryId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance == null ? null : balance.trim();
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power == null ? null : power.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}