/* -------------------------------------------
 * StationInfo.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 桩站信息表 v1.0
 */


public class StationInfoOut {

    //主键-桩站ID
    @ApiModelProperty(value = "桩站id")
    private Integer id;

    //公司ID
    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    //桩站余额
    @ApiModelProperty(value = "桩站余额")
    private BigDecimal useBalance;

    //未结算金额
    @ApiModelProperty(value = "未结算金额")
    private BigDecimal noCheckBalance;

    //冻结金额（保留-预留给提现用）
    @ApiModelProperty(value = "冻结金额（保留-预留给提现用）")
    private BigDecimal lockedBalance;

    //充电单价(元/度)
    @ApiModelProperty(value = "充电单价(元/度)")
    private BigDecimal chargePrice;

    //服务费(元/度)
    @ApiModelProperty(value = "服务费(元/度)")
    private BigDecimal servicePrice;

    //状态(0不可用，1可用)
    @ApiModelProperty(value = "状态(0不可用，1可用)")
    private Integer status;

    //是否删除(0否，1是)
    @ApiModelProperty(value = "是否删除(0否，1是)")
    private Integer delFlag;

    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String name;

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getUseBalance() {
        return useBalance;
    }

    public void setUseBalance(BigDecimal useBalance) {
        this.useBalance = useBalance;
    }

    public BigDecimal getNoCheckBalance() {
        return noCheckBalance;
    }

    public void setNoCheckBalance(BigDecimal noCheckBalance) {
        this.noCheckBalance = noCheckBalance;
    }

    public BigDecimal getLockedBalance() {
        return lockedBalance;
    }

    public void setLockedBalance(BigDecimal lockedBalance) {
        this.lockedBalance = lockedBalance;
    }

    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}