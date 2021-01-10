/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 充电枪状态数量统计
 */
@ApiModel
public class GunStatusStatisticsOut {

    //充电枪总数
    @ApiModelProperty(value = "全部")
    private Integer allTotalCount;
    //离线充电枪总数
    @ApiModelProperty(value = "离线")
    private Integer offlineTotalCount;
    //空闲中充电枪总数
    @ApiModelProperty(value = "空闲")
    private Integer freeTotalCount;
    //连接中充电枪总数
    @ApiModelProperty(value = "连接中")
    private Integer linkingTotalCount;
    //充电中充电枪总数
    @ApiModelProperty(value = "充电中")
    private Integer chargingTotalCount;
    //被预约充电枪总数
    @ApiModelProperty(value = "被预约")
    private Integer bookedTotalCount;
    //排队中充电枪总数
    @ApiModelProperty(value = "排队中")
    private Integer lineTotalCount;

    public Integer getAllTotalCount() {
        return allTotalCount;
    }

    public void setAllTotalCount(Integer allTotalCount) {
        this.allTotalCount = allTotalCount;
    }

    public Integer getOfflineTotalCount() {
        return offlineTotalCount;
    }

    public void setOfflineTotalCount(Integer offlineTotalCount) {
        this.offlineTotalCount = offlineTotalCount;
    }

    public Integer getFreeTotalCount() {
        return freeTotalCount;
    }

    public void setFreeTotalCount(Integer freeTotalCount) {
        this.freeTotalCount = freeTotalCount;
    }

    public Integer getLinkingTotalCount() {
        return linkingTotalCount;
    }

    public void setLinkingTotalCount(Integer linkingTotalCount) {
        this.linkingTotalCount = linkingTotalCount;
    }

    public Integer getChargingTotalCount() {
        return chargingTotalCount;
    }

    public void setChargingTotalCount(Integer chargingTotalCount) {
        this.chargingTotalCount = chargingTotalCount;
    }

    public Integer getBookedTotalCount() {
        return bookedTotalCount;
    }

    public void setBookedTotalCount(Integer bookedTotalCount) {
        this.bookedTotalCount = bookedTotalCount;
    }

    public Integer getLineTotalCount() {
        return lineTotalCount;
    }

    public void setLineTotalCount(Integer lineTotalCount) {
        this.lineTotalCount = lineTotalCount;
    }

}