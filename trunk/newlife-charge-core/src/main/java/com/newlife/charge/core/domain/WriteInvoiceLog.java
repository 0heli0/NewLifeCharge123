/* -------------------------------------------
 * WriteInvoiceLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 发票记录表 v1.0
 */


public class WriteInvoiceLog {

    //主键-发票记录ID
    private Integer id;

    //用户ID
    private Integer userId;

    //申请流水号
    private String invoiceSn;

    //发票面额
    private BigDecimal amount;

    //发票状态（1：待发送 2：已发送）
    private Integer status;

    //收货地址
    private String receiverAddress;

    //快递类型
    private Integer expressCompanyType;

    //快递单号
    private String expressNo;

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

    public String getInvoiceSn() {
        return invoiceSn;
    }

    public void setInvoiceSn(String invoiceSn) {
        this.invoiceSn = invoiceSn == null ? null : invoiceSn.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public Integer getExpressCompanyType() {
        return expressCompanyType;
    }

    public void setExpressCompanyType(Integer expressCompanyType) {
        this.expressCompanyType = expressCompanyType;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}