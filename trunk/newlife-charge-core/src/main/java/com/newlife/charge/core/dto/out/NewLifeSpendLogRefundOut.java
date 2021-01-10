/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


/**
 * (车主)用户余额退款详情
 */
@ApiModel
public class NewLifeSpendLogRefundOut {

    //资金流水记录id
    @ApiModelProperty(value = "资金流水记录id")
    private Integer id;

    //资金流水号
    @ApiModelProperty(value = "资金流水号")
    private String moneySn;

    //类型
    @ApiModelProperty(value = "类型")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注
    @ApiModelProperty(value = "类型备注")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //操作金额/金额
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    //实付金额
    @ApiModelProperty(value = "实付金额")
    private BigDecimal totalPrice;

    //用户id(车主)
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    //用户手机号码
    @ApiModelProperty(value = "用户手机号码")
    private String userMobile;

    //用户历史余额
    @ApiModelProperty(value = "之前剩余金额")
    private BigDecimal userOldBalance;

    //用户当前余额/剩余金额
    @ApiModelProperty(value = "剩余金额")
    private BigDecimal userNowBalance;

    //优惠券减免金额
    @ApiModelProperty(value = "优惠券减免金额")
    private BigDecimal couponPrice;

    //支付方式
    @ApiModelProperty(value = "支付方式编码")
    private Integer payType;

    //支付方式
    @ApiModelProperty(value = "支付方式")
    private String payTypeName;

    //创建时间
    @ApiModelProperty(value = "时间")
    //时间格式化，时区使用东八区
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

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
        this.moneySn = moneySn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(Integer typeRemark) {
        this.typeRemark = typeRemark;
    }

    public String getTypeRemarkName() {
        return typeRemarkName;
    }

    public void setTypeRemarkName(String typeRemarkName) {
        this.typeRemarkName = typeRemarkName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}