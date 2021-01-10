/* -------------------------------------------
 * StationTimePrice.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * 充电站价格表 v1.0
 */
public class StationTimePriceIn {

    //充电站时段价格表主键ID
    private Integer id;

    //所属桩站id
    private Integer stationId;

    //时段
    private Integer timeNo;

    @ApiModelProperty(value = "时段")
    @NotNull(message = "时段")
    private List<StationTimeIn> stationTimeInList;

    //涨幅(%):+涨-减
    @ApiModelProperty(value = "涨幅(%):+涨-减")
    @NotNull(message = "涨幅(%):+涨-减")
    private Integer increase;

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

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    public List<StationTimeIn> getStationTimeInList() {
        return stationTimeInList;
    }

    public void setStationTimeInList(List<StationTimeIn> stationTimeInList) {
        this.stationTimeInList = stationTimeInList;
    }
}