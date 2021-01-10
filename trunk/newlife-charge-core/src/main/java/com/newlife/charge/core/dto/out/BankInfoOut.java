/* -------------------------------------------
 * Bank.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-25 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 银行信息
 */
@ApiModel
public class BankInfoOut {

    //ID
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    //银行名称
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    //银行Logo
    @ApiModelProperty(value = "银行Logo图片地址")
    private String bankLogoImg;

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
        this.bankName = bankName;
    }

    public String getBankLogoImg() {
        return bankLogoImg;
    }

    public void setBankLogoImg(String bankLogoImg) {
        this.bankLogoImg = bankLogoImg;
    }
}