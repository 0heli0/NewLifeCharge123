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

import java.util.Date;


/**
 * 总后台 审核通过的公司详情
 */
@ApiModel
public class GeneralCompanyInfoOut{

    //公司ID
    @ApiModelProperty(value = "公司ID")
    private Integer id;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    //统一社会信用代码
    @ApiModelProperty(value = "统一社会信用代码")
    private String companyCode;

    //管理员姓名
    @ApiModelProperty(value = "管理员姓名")
    private String managerName;

    //管理员身份证号
    @ApiModelProperty(value = "管理员身份证号")
    private String managerIdCardNo;

    //银行ID
    @ApiModelProperty(value = "银行ID")
    private Integer bankId;

    //银行名称
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    //银行卡账号
    @ApiModelProperty(value = "银行卡账号")
    private String debitCardNo;

    //开户省ID
    @ApiModelProperty(value = "开户省ID")
    private Integer bankProvinceId;

    //开户省名称
    @ApiModelProperty(value = "开户省名称")
    private String bankProvinceName;

    //开户市ID
    @ApiModelProperty(value = "开户市ID")
    private Integer bankCityId;

    //开户市名称
    @ApiModelProperty(value = "开户市名称")
    private String bankCityName;

    //开户支行名称
    @ApiModelProperty(value = "开户支行名称")
    private String subBankName;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //营业执照
    @ApiModelProperty(value = "营业执照")
    private String companyImg;

    //管理员手持身份证照片（身份证正面+本人拍照）
    @ApiModelProperty(value = "管理员手持身份证照片正面")
    private String managerHandIdCardImg;

    //管理员手持身份证照片反面（身份证反面+本人拍照）
    @ApiModelProperty(value = "管理员手持身份证照片反面")
    private String managerHandIdCardImgBack;

    //绑定的桩站数量
    @ApiModelProperty(value = "绑定的桩站数量")
    private Integer stationInfoCount;
    //总车位数量
    @ApiModelProperty(value = "总车位数量")
    private Integer stationParkingLotCount;
    //总充电桩数量
    @ApiModelProperty(value = "总充电桩数量")
    private Integer stationClientCount;
    //总充电枪数量
    @ApiModelProperty(value = "总充电枪数量")
    private Integer stationClientGunCount;


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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getBankProvinceName() {
        return bankProvinceName;
    }

    public void setBankProvinceName(String bankProvinceName) {
        this.bankProvinceName = bankProvinceName;
    }

    public Integer getBankCityId() {
        return bankCityId;
    }

    public void setBankCityId(Integer bankCityId) {
        this.bankCityId = bankCityId;
    }

    public String getBankCityName() {
        return bankCityName;
    }

    public void setBankCityName(String bankCityName) {
        this.bankCityName = bankCityName;
    }

    public String getSubBankName() {
        return subBankName;
    }

    public void setSubBankName(String subBankName) {
        this.subBankName = subBankName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getStationInfoCount() {
        return stationInfoCount;
    }

    public void setStationInfoCount(Integer stationInfoCount) {
        this.stationInfoCount = stationInfoCount;
    }

    public Integer getStationParkingLotCount() {
        return stationParkingLotCount;
    }

    public void setStationParkingLotCount(Integer stationParkingLotCount) {
        this.stationParkingLotCount = stationParkingLotCount;
    }

    public Integer getStationClientCount() {
        return stationClientCount;
    }

    public void setStationClientCount(Integer stationClientCount) {
        this.stationClientCount = stationClientCount;
    }

    public Integer getStationClientGunCount() {
        return stationClientGunCount;
    }

    public void setStationClientGunCount(Integer stationClientGunCount) {
        this.stationClientGunCount = stationClientGunCount;
    }
}