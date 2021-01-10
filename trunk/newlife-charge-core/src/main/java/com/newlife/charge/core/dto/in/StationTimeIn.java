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


/**
 * 充电站价格表 v1.0
 */
public class StationTimeIn {

    //时段id
    @ApiModelProperty(value = "时段id")
    private Integer id;

    //开始时段
    @ApiModelProperty(value = "开始时段")
    @NotNull(message = "开始时段")
    private Time timeBegin;

    //结束时段
    @ApiModelProperty(value = "结束时段")
    @NotNull(message = "结束时段")
    private Time timeEnd;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}