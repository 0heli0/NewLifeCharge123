/* -------------------------------------------
 * StationTimePrice.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;


/**
 * 用户端充电站详情 v1.0
 */
@Getter
@Setter
@ApiModel(value = "用户端充电站详情")
public class ClientStationDetailOut {

    @ApiModelProperty(value = "基础电价")
    private BigDecimal chargePrice;

    @ApiModelProperty(value = "最终电价")
    private BigDecimal price;

    @ApiModelProperty(value = "服务费")
    private BigDecimal servicePrice;

    @ApiModelProperty(value = "是否有阶梯电价(0:没有,1:有)")
    private int isTimePrice;

    @ApiModelProperty(value = "阶梯电价描述")
    private String timePriceDescription;

    @ApiModelProperty(value = "停车场收费说明")
    private String parkingFee;

    @ApiModelProperty(value = "桩站现场管理公司")
    private String managers;

    @ApiModelProperty(value = "联系方式/电话")
    private String tel;

    public BigDecimal getPrice() {
        if(price != null){
            price = price.setScale(4,BigDecimal.ROUND_HALF_UP);
        }
        return price;
    }

    public String getTimePriceDescription() {
        if(isTimePrice == 0){
            timePriceDescription = "全天价格统一";
        }else if(isTimePrice == 1){
            timePriceDescription = "全站实行时段电费";
        }
        return timePriceDescription;
    }
}