/* -------------------------------------------
 * RechargeLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 车主用户充值记录表 v1.0
 */


public class RechargeLog {

    //主键-用户充值订单id
    private Integer id;

    //订单id
    private Integer orderId;

    //订单编号
    private String orderSn;

    //用户id（车主用户）
    private Integer userId;

    //预充值套餐id
    private Integer preChargeId;

    //预充值套餐原价金额
    private BigDecimal originalPrice;

    //预充值套餐最终金额
    private BigDecimal finalPrice;

    //用户领取优惠券id
    private Integer userCouponId;

    //优惠券减免价格
    private BigDecimal couponPrice;

    //实付金额
    private BigDecimal totalPrice;

    //支付时间
    private Date payTime;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPreChargeId() {
        return preChargeId;
    }

    public void setPreChargeId(Integer preChargeId) {
        this.preChargeId = preChargeId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RechargeLog{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderSn='" + orderSn + '\'' +
                ", userId=" + userId +
                ", preChargeId=" + preChargeId +
                ", originalPrice=" + originalPrice +
                ", finalPrice=" + finalPrice +
                ", userCouponId=" + userCouponId +
                ", couponPrice=" + couponPrice +
                ", totalPrice=" + totalPrice +
                ", payTime=" + payTime +
                ", createTime=" + createTime +
                '}';
    }
}