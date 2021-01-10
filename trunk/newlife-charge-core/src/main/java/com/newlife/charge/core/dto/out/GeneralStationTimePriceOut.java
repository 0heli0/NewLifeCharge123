/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;


/**
 * 总后台 时段电价
 */
@ApiModel
public class GeneralStationTimePriceOut {

    //充电站时段电价ID
    @ApiModelProperty(value = "充电站时段电价ID")
    private Integer id;

    //所属桩站id
    @ApiModelProperty(value = "所属桩站id")
    private Integer stationId;
    //时段序号
    @ApiModelProperty(value = "时段序号")
    private Integer timeNo;

    //开始时段
    @ApiModelProperty(value = "开始时段,格式HH:mm:ss")
    private Time timeBegin;
//    private String timeBeginStr;

    //结束时段
    @ApiModelProperty(value = "结束时段,格式HH:mm:ss")
    private Time timeEnd;
//    private String timeEndStr;

    //涨幅(%):+涨-减
    @ApiModelProperty(value = "涨幅(%):+涨-减")
    private Integer increase;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

//    public String getTimeBeginStr() {
//        return timeBeginStr;
//    }
//
//    public void setTimeBeginStr(String timeBeginStr) {
//        this.timeBeginStr = timeBeginStr;
//    }
//
//    public String getTimeEndStr() {
//        return timeEndStr;
//    }
//
//    public void setTimeEndStr(String timeEndStr) {
//        this.timeEndStr = timeEndStr;
//    }
}