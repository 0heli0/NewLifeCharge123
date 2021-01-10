/* -------------------------------------------
 * CouponUserReadRef.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-26 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;


/**
 * 优惠券用户关联表 v1.0
 */


public class CouponUserReadRef {

    //主键-优惠券适用桩站ID
    private Integer id;

    //优惠券ID
    private Integer couponId;

    //适用桩站ID
    private Integer userId;

    // 是否已读
    private Integer isRead;

    //创建时间
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}