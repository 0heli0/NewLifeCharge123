/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 充电枪 简单信息（充电枪编号,状态，电费）
 */
@ApiModel
public class StationClientGunInfoSimpleOut {


    //充电枪编号（自定义）
    @ApiModelProperty(value = "充电枪编号")
    private String code;

    //终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    @ApiModelProperty(value = "终端状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private Integer status;

    //终端状态名称
    @ApiModelProperty(value = "终端状态名称(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private String statusName;

    //当前充电单价(元/度)
    @ApiModelProperty(value = "电费")
    private BigDecimal currentChargePrice;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getCurrentChargePrice() {
        return currentChargePrice;
    }

    public void setCurrentChargePrice(BigDecimal currentChargePrice) {
        this.currentChargePrice = currentChargePrice;
    }
}