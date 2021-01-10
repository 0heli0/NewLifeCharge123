/* -------------------------------------------
 * UserAccount.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-30 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 用户账户表 v1.0
 */


public class UserAccount {

    //主键-用户账户ID
    private Integer id;

    //用户ID
    private Integer userId;

    //充值金额-未参与优惠
    private BigDecimal chargeBalance;

    //优惠充值金额-使用优惠券实付金额
    private BigDecimal couponChargeBalance;

    //赠送金额-优惠券赠送金额
    private BigDecimal giveBalance;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getChargeBalance() {
        return chargeBalance;
    }

    public void setChargeBalance(BigDecimal chargeBalance) {
        this.chargeBalance = chargeBalance;
    }

    public BigDecimal getCouponChargeBalance() {
        return couponChargeBalance;
    }

    public void setCouponChargeBalance(BigDecimal couponChargeBalance) {
        this.couponChargeBalance = couponChargeBalance;
    }

    public BigDecimal getGiveBalance() {
        return giveBalance;
    }

    public void setGiveBalance(BigDecimal giveBalance) {
        this.giveBalance = giveBalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}