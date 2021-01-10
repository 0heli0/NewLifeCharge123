/* -------------------------------------------
 * StationTimePrice.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.sql.Time;
import java.util.Date;



/**
 * 充电站价格表 v1.0
 */
public class StationTimePrice {

    //充电站时段价格表主键ID
    private Integer id;

    //所属桩站id
    private Integer stationId;

    //时段
    private Integer timeNo;

    //开始时段
    private Time timeBegin;

    //结束时段
    private Time timeEnd;

    //涨幅(%):+涨-减
    private Integer increase;

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

    public Integer getTimeNo() {
        return timeNo;
    }

    public void setTimeNo(Integer timeNo) {
        this.timeNo = timeNo;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setTimeBegin(Time timeBegin) {
        this.timeBegin = timeBegin;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }
}