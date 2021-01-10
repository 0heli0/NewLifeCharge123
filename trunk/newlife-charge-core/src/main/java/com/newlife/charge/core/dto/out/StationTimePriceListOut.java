/* -------------------------------------------
 * StationTimePrice.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.newlife.charge.core.dto.in.StationTimeIn;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


/**
 * 充电站 时段价格表 v1.0
 */
public class StationTimePriceListOut {


    @ApiModelProperty(value = "时段")
    private List<StationTimeIn> stationTimeInList;

    //涨幅(%):+涨-减
    @ApiModelProperty(value = "涨幅(%):+涨-减")
    private Integer increase;

    //调整后价格
    @ApiModelProperty(value = "涨幅(%):+涨-减")
    private BigDecimal price;

    //时段
    @ApiModelProperty(value = "时段")
    private Integer timeNo;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTimeNo() {
        return timeNo;
    }

    public void setTimeNo(Integer timeNo) {
        this.timeNo = timeNo;
    }
}