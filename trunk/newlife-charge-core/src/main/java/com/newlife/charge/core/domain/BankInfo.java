/* -------------------------------------------
 * BankInfo.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 银行信息表 v1.0
 */


public class BankInfo {

    //主键-银行信息id
    private Integer id;

    //银行名称
    private String bankName;

    //银行Logo
    private String bankLogoImg;

    //父节点
    private Integer parentId;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankLogoImg() {
        return bankLogoImg;
    }

    public void setBankLogoImg(String bankLogoImg) {
        this.bankLogoImg = bankLogoImg == null ? null : bankLogoImg.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}