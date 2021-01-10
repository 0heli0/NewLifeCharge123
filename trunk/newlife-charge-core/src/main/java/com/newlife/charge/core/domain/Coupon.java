/* -------------------------------------------
 * Coupon.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 优惠券表 v1.0
 */


public class Coupon {

    //主键-优惠券ID
    private Integer id;

    //所属桩站ID
    private Integer stationId;

    //优惠券类型（1：充值通用优惠券 2：用电通用优惠券）
    private Integer type;

    // 优惠券适用范围类型(0: 全部-通用优惠券, 1: 指定桩站适用-用电优惠券)
    private Integer scopeType;

    //面额
    private BigDecimal price;

    //门槛金额
    private BigDecimal thresholdPrice;

    //总数
    private Integer totalNumber;

    //限领
    private Integer limitNumber;

    //剩余数量
    private Integer leftNumber;

    //状态(0：生效中 1：已过期 2：已删除)
    private Integer status;

    //领取开始时间
    private Date collectStartTime;

    //领取结束时间
    private Date collectEndTime;

    //使用开始时间
    private Date useStartTime;

    //使用结束时间
    private Date useEndTime;

    //创建时间
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getScopeType() {
        return scopeType;
    }

    public void setScopeType(Integer scopeType) {
        this.scopeType = scopeType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getThresholdPrice() {
        return thresholdPrice;
    }

    public void setThresholdPrice(BigDecimal thresholdPrice) {
        this.thresholdPrice = thresholdPrice;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }

    public Integer getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(Integer leftNumber) {
        this.leftNumber = leftNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCollectStartTime() {
        return collectStartTime;
    }

    public void setCollectStartTime(Date collectStartTime) {
        this.collectStartTime = collectStartTime;
    }

    public Date getCollectEndTime() {
        return collectEndTime;
    }

    public void setCollectEndTime(Date collectEndTime) {
        this.collectEndTime = collectEndTime;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}