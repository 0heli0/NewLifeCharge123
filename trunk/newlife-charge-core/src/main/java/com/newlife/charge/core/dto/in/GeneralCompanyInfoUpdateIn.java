/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 总后台 审核通过的公司信息修改
 */
@ApiModel
public class GeneralCompanyInfoUpdateIn {

    //公司ID
    @ApiModelProperty(value = "公司ID,必传")
    @NotNull(message = "公司ID不能为空")
    private Integer id;

    //公司名称
    @ApiModelProperty(value = "公司名称，必传，最大长度100")
    @NotBlank(message = "公司名称不能为空")
    @Length(max = 100,message = "公司名称最大长度不能超过100位")
    private String companyName;

    //统一社会信用代码
    @ApiModelProperty(value = "统一社会信用代码，必传，最大长度50")
    @NotBlank(message = "统一社会信用代码不能为空")
    @Length(max = 50,message = "统一社会信用代码最大长度不能超过50位")
    private String companyCode;

    //管理员姓名
    @ApiModelProperty(value = "管理员姓名，必传，最大长度50")
    @NotBlank(message = "管理员姓名不能为空")
    @Length(max = 50,message = "管理员姓名最大长度不能超过50位")
    private String managerName;

    //管理员身份证号
    @ApiModelProperty(value = "管理员身份证号，必传，最大长度18")
    @NotBlank(message = "管理员身份证号不能为空")
    @Length(max = 18,message = "管理员身份证号最大长度不能超过18位")
    private String managerIdCardNo;

    //银行ID
    @ApiModelProperty(value = "银行ID,必传")
    @NotNull(message = "银行ID不能为空")
    private Integer bankId;

    //银行卡账号
    @ApiModelProperty(value = "银行卡账号，必传，最大长度35")
    @NotBlank(message = "银行卡账号不能为空")
    @Length(max = 35,message = "银行卡账号最大长度不能超过35位")
    private String debitCardNo;

    //开户省ID
    @ApiModelProperty(value = "开户省ID,必传")
    @NotNull(message = "开户省ID不能为空")
    private Integer bankProvinceId;


    //开户市ID
    @ApiModelProperty(value = "开户市ID,必传")
    @NotNull(message = "开户市ID不能为空")
    private Integer bankCityId;

    //开户支行名称
    @ApiModelProperty(value = "开户支行名称，必传，最大长度20")
    @NotBlank(message = "开户支行名称不能为空")
    @Length(max = 20,message = "开户支行名称最大长度不能超过20位")
    private String subBankName;

    //营业执照
    @ApiModelProperty(value = "营业执照，必传，最大长度500")
    @NotBlank(message = "营业执照不能为空")
    @Length(max = 500,message = "营业执照最大长度不能超过500位")
    private String companyImg;

    //管理员手持身份证照片（身份证正面+本人拍照）
    @ApiModelProperty(value = "管理员手持身份证照片正面，必传，最大长度500")
    @NotBlank(message = "管理员手持身份证照片正面不能为空")
    @Length(max = 500,message = "管理员手持身份证照片正面最大长度不能超过500位")
    private String managerHandIdCardImg;

    //管理员手持身份证照片反面（身份证反面+本人拍照）
    @ApiModelProperty(value = "管理员手持身份证照片反面，必传，最大长度500")
    @NotBlank(message = "管理员手持身份证照片反面不能为空")
    @Length(max = 500,message = "管理员手持身份证照片反面最大长度不能超过500位")
    private String managerHandIdCardImgBack;


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
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerIdCardNo() {
        return managerIdCardNo;
    }

    public void setManagerIdCardNo(String managerIdCardNo) {
        this.managerIdCardNo = managerIdCardNo;
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
        this.debitCardNo = debitCardNo;
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
        this.subBankName = subBankName;
    }

    public String getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public String getManagerHandIdCardImg() {
        return managerHandIdCardImg;
    }

    public void setManagerHandIdCardImg(String managerHandIdCardImg) {
        this.managerHandIdCardImg = managerHandIdCardImg;
    }

    public String getManagerHandIdCardImgBack() {
        return managerHandIdCardImgBack;
    }

    public void setManagerHandIdCardImgBack(String managerHandIdCardImgBack) {
        this.managerHandIdCardImgBack = managerHandIdCardImgBack;
    }
}