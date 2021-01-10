/* -------------------------------------------
 * NewLifeSpendLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 新活资金流水记录表 v1.0
 * 如果类型是1、2、3,则用户ID应该是车主ID
 * 如果类型是4、5，则用户ID应该是此桩站的主账号(注册账号)
 */
public class NewLifeSpendLog {

    //主键-新活资金流水记录id
    private Integer id;

    //资金流水号
    private String moneySn;

    //订单id
    private Integer orderId;

    //订单号
    private String orderSn;

    //类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)
    private Integer type;

    //类型备注(1:充值,2:消费,3:退款,4:卖电收入,5:服务费收入)
    private Integer typeRemark;

    //操作金额
    private BigDecimal amount;

    //用户id
    private Integer userId;

    //用户类型
    private Integer userType;

    //用户姓名
    private String userName;

    //用户手机号码
    private String userMobile;

    //微信头像
    private String userAvatarUrl;

    //用户历史余额
    private BigDecimal userOldBalance;

    //用户可得金额
    private BigDecimal userGainAmount;

    //用户当前余额
    private BigDecimal userNowBalance;

    //桩站id
    private Integer stationId;

    //桩站名称
    private String stationName;

    //抽佣比
    private BigDecimal commissionRation;

    //服务费
    private BigDecimal servicePrice;

    //桩站可得总金额
    private BigDecimal stationGainAmount;

    //桩站可得电费
    private BigDecimal stationGainAmountCharge;

    //桩站可得服务费
    private BigDecimal stationGainAmountService;

    //桩站历史未结算金额
    private BigDecimal stationOldNoCheckAmount;

    //桩站未结算金额
    private BigDecimal stationNowNoCheckAmount;

    //桩站历史余额
    private BigDecimal stationOldBalance;

    //桩站余额
    private BigDecimal stationNowBalance;

    //新活可得金额
    private BigDecimal newLifeGainAmount;

    //针对充电消费-优惠券减免金额
    private BigDecimal couponAmount;

    //针对充电消费-优惠券类型(2-用电通用优惠券，3-桩站用电优惠券)
    private Integer couponType;

    //备注
    private String remark;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoneySn() {
        return moneySn;
    }

    public void setMoneySn(String moneySn) {
        this.moneySn = moneySn == null ? null : moneySn.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(Integer typeRemark) {
        this.typeRemark = typeRemark;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl == null ? null : userAvatarUrl.trim();
    }

    public BigDecimal getUserOldBalance() {
        return userOldBalance;
    }

    public void setUserOldBalance(BigDecimal userOldBalance) {
        this.userOldBalance = userOldBalance;
    }

    public BigDecimal getUserNowBalance() {
        return userNowBalance;
    }

    public void setUserNowBalance(BigDecimal userNowBalance) {
        this.userNowBalance = userNowBalance;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public BigDecimal getCommissionRation() {
        return commissionRation;
    }

    public void setCommissionRation(BigDecimal commissionRation) {
        this.commissionRation = commissionRation;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public BigDecimal getStationGainAmount() {
        return stationGainAmount;
    }

    public void setStationGainAmount(BigDecimal stationGainAmount) {
        this.stationGainAmount = stationGainAmount;
    }

    public BigDecimal getStationOldNoCheckAmount() {
        return stationOldNoCheckAmount;
    }

    public void setStationOldNoCheckAmount(BigDecimal stationOldNoCheckAmount) {
        this.stationOldNoCheckAmount = stationOldNoCheckAmount;
    }

    public BigDecimal getStationNowNoCheckAmount() {
        return stationNowNoCheckAmount;
    }

    public void setStationNowNoCheckAmount(BigDecimal stationNowNoCheckAmount) {
        this.stationNowNoCheckAmount = stationNowNoCheckAmount;
    }

    public BigDecimal getStationOldBalance() {
        return stationOldBalance;
    }

    public void setStationOldBalance(BigDecimal stationOldBalance) {
        this.stationOldBalance = stationOldBalance;
    }

    public BigDecimal getStationNowBalance() {
        return stationNowBalance;
    }

    public void setStationNowBalance(BigDecimal stationNowBalance) {
        this.stationNowBalance = stationNowBalance;
    }

    public BigDecimal getNewLifeGainAmount() {
        return newLifeGainAmount;
    }

    public void setNewLifeGainAmount(BigDecimal newLifeGainAmount) {
        this.newLifeGainAmount = newLifeGainAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public BigDecimal getUserGainAmount() {
        return userGainAmount;
    }

    public void setUserGainAmount(BigDecimal userGainAmount) {
        this.userGainAmount = userGainAmount;
    }

    public BigDecimal getStationGainAmountCharge() {
        return stationGainAmountCharge;
    }

    public void setStationGainAmountCharge(BigDecimal stationGainAmountCharge) {
        this.stationGainAmountCharge = stationGainAmountCharge;
    }

    public BigDecimal getStationGainAmountService() {
        return stationGainAmountService;
    }

    public void setStationGainAmountService(BigDecimal stationGainAmountService) {
        this.stationGainAmountService = stationGainAmountService;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }
}