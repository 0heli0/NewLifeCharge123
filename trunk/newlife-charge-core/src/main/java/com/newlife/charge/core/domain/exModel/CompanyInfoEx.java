/* -------------------------------------------
 * CompanyInfo.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.CompanyInfo;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 桩站运营公司信息表 v1.0
 */


public class CompanyInfoEx extends CompanyInfo{

    //开户省名称
    @ApiModelProperty(value = "开户省名称")
    private String bankProvinceName;

    //开户市名称
    @ApiModelProperty(value = "开户市名称")
    private String bankCityName;

    //开户市名称
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    public String getBankProvinceName() {
        return bankProvinceName;
    }

    public void setBankProvinceName(String bankProvinceName) {
        this.bankProvinceName = bankProvinceName;
    }

    public String getBankCityName() {
        return bankCityName;
    }

    public void setBankCityName(String bankCityName) {
        this.bankCityName = bankCityName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}