/* -------------------------------------------
 * UserCoupon.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 用户领取优惠券表 v1.0
 */


public class UserCoupon {

    //主键-用户领取优惠券ID
    private Integer id;

    //优惠券ID
    private Integer couponId;

    //用户ID
    private Integer userId;

    //领取流水号
    private String serialNumber;

    //优惠券状态（0：未使用 1：已使用 2:冻结 3:已过期）
    private Integer status;

    //冻结时间
    private Date frozenTime;

    //对应的订单
    private String orderSn;

    //领取时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFrozenTime() {
        return frozenTime;
    }

    public void setFrozenTime(Date frozenTime) {
        this.frozenTime = frozenTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}