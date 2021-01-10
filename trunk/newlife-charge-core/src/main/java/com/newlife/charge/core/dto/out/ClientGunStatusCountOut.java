/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 充电枪各个状态数量
 */
@ApiModel
public class ClientGunStatusCountOut {

    //充电枪使用中数量
    @ApiModelProperty(value = "充电枪使用中数量")
    private Integer usedCount;

    //闲置数量
    @ApiModelProperty(value = "闲置数量")
    private Integer unusedCount;

    //全部数量
    @ApiModelProperty(value = "全部数量")
    private Integer totalCount;

    //连接中数量
    @ApiModelProperty(value = "连接中数量")
    private Integer linkCount;

    //充电中数量
    @ApiModelProperty(value = "充电中数量")
    private Integer chargerCount;

    //排队中数量
    @ApiModelProperty(value = "排队中数量")
    private Integer lineUpCount;

    //预约数量
    @ApiModelProperty(value = "预约数量")
    private Integer appointmentCount;

    //离线数量
    @ApiModelProperty(value = "离线数量")
    private Integer offLindCount;

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Integer getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(Integer unusedCount) {
        this.unusedCount = unusedCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(Integer linkCount) {
        this.linkCount = linkCount;
    }

    public Integer getChargerCount() {
        return chargerCount;
    }

    public void setChargerCount(Integer chargerCount) {
        this.chargerCount = chargerCount;
    }

    public Integer getLineUpCount() {
        return lineUpCount;
    }

    public void setLineUpCount(Integer lineUpCount) {
        this.lineUpCount = lineUpCount;
    }

    public Integer getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Integer appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public Integer getOffLindCount() {
        return offLindCount;
    }

    public void setOffLindCount(Integer offLindCount) {
        this.offLindCount = offLindCount;
    }
}