/* -------------------------------------------
 * StationTimePrice.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import java.sql.Time;
import java.util.Date;


/**
 * 充电站价格表 v1.0
 */
public class StationTimePriceQuery {

    //所属桩站id
    private Integer stationId;

    //时段
    private Integer timeNo;

    //开始时段
    private Time timeBegin;

    //结束时段
    private Time timeEnd;

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

    public Time getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Time timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }
}