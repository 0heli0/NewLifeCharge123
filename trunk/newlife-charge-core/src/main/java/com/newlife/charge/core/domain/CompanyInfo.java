/* -------------------------------------------
 * CompanyInfo.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 桩站运营公司信息表 v1.0
 */


public class CompanyInfo {

    //主键-公司ID
    private Integer id;
    //用户ID(桩站主账号)
    private Integer userId;

    //公司名称
    private String companyName;

    //统一社会信用代码
    private String companyCode;

    //营业执照
    private String companyImg;

    //管理员姓名
    private String managerName;

    //管理员身份证号
    private String managerIdCardNo;

    //管理员手持身份证照片（身份证正面+本人拍照）
    private String managerHandIdCardImg;

    //管理员手持身份证照片反面（身份证反面+本人拍照）
    private String managerHandIdCardImgBack;

    //银行ID
    private Integer bankId;

    //银行卡账号
    private String debitCardNo;

    //开户省ID
    private Integer bankProvinceId;

    //开户市ID
    private Integer bankCityId;

    //开户支行名称
    private String subBankName;

    //审核状态：0审核中 1审核通过 2审核不通过
    private Integer auditStatus;

    //审核时间
    private Date auditTime;

    //备注
    private String remark;

    //抽佣比例(%)
    private BigDecimal commissionRation;

    //创建时间
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg == null ? null : companyImg.trim();
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getManagerIdCardNo() {
        return managerIdCardNo;
    }

    public void setManagerIdCardNo(String managerIdCardNo) {
        this.managerIdCardNo = managerIdCardNo == null ? null : managerIdCardNo.trim();
    }

    public String getManagerHandIdCardImg() {
        return managerHandIdCardImg;
    }

    public void setManagerHandIdCardImg(String managerHandIdCardImg) {
        this.managerHandIdCardImg = managerHandIdCardImg == null ? null : managerHandIdCardImg.trim();
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getDebitCardNo() {
        return debitCardNo;
    }

    public void setDebitCardNo(String debitCardNo) {
        this.debitCardNo = debitCardNo == null ? null : debitCardNo.trim();
    }

    public Integer getBankProvinceId() {
        return bankProvinceId;
    }

    public void setBankProvinceId(Integer bankProvinceId) {
        this.bankProvinceId = bankProvinceId;
    }

    public Integer getBankCityId() {
        return bankCityId;
    }

    public void setBankCityId(Integer bankCityId) {
        this.bankCityId = bankCityId;
    }

    public String getSubBankName() {
        return subBankName;
    }

    public void setSubBankName(String subBankName) {
        this.subBankName = subBankName == null ? null : subBankName.trim();
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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

    public String getManagerHandIdCardImgBack() {
        return managerHandIdCardImgBack;
    }

    public void setManagerHandIdCardImgBack(String managerHandIdCardImgBack) {
        this.managerHandIdCardImgBack = managerHandIdCardImgBack;
    }


    public BigDecimal getCommissionRation() {
        return commissionRation;
    }

    public void setCommissionRation(BigDecimal commissionRation) {
        this.commissionRation = commissionRation;
    }

}