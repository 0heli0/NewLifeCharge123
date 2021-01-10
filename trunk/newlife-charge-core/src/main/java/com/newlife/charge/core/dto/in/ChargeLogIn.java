/* -------------------------------------------
 * ChargeLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.DateUtils;
import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 车主用户充电记录表 查询接收类 v1.0
 */


public class ChargeLogIn extends Pageable {

    //充电记录id
    @ApiModelProperty(value = "充电记录id", required = true)
    private Integer id;

    //桩站id
    @ApiModelProperty(value = "桩站id", required = true)
    private Integer stationId;

    //查询时间
    @ApiModelProperty(value = "查询时间", required = true)
    private String dateTime;

    //显示条数
    @ApiModelProperty(value = "显示条数", required = true)
    private Integer limitNo;

    //开始时间
    @ApiModelProperty(value = "开始时间", required = true)
    private Date startTime;

    //结束时间
    @ApiModelProperty(value = "结束时间", required = true)
    private Date endTime;

    //充电方式
    @ApiModelProperty(value = "充电方式", required = true)
    private Integer clientChargeType;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getLimitNo() {
        return limitNo;
    }

    public void setLimitNo(Integer limitNo) {
        this.limitNo = limitNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientChargeType() {
        return clientChargeType;
    }

    public void setClientChargeType(Integer clientChargeType) {
        this.clientChargeType = clientChargeType;
    }
}