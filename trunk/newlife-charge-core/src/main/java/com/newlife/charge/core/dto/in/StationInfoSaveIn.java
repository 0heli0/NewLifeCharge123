/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 站点/桩站信息 新增
 */
@ApiModel
public class StationInfoSaveIn {

    //所属公司ID
    @ApiModelProperty(value = "所属公司ID，必传")
    @NotNull(message = "请选择所属公司")
    private Integer companyId;

    //桩站名称
    @ApiModelProperty(value = "桩站名称，必传,最长100位")
    @NotBlank(message = "请填写桩站名称")
    @Length(max = 100, message = "桩站名称长度最长100位")
    private String name;
    //桩站地址
    @ApiModelProperty(value = "桩站地址，必传,最长200位")
    @NotBlank(message = "请填写桩站地址")
    @Length(max = 200, message = "桩站地址长度最长200位")
    private String address;

    //纬度
    @ApiModelProperty(value = "纬度，必传,3位整数9位小数")
    @NotNull(message = "请填写纬度")
    private BigDecimal lat;
    //经度
    @ApiModelProperty(value = "经度，必传,3位整数9位小数")
    @NotNull(message = "请填写经度")
    private BigDecimal lng;


    //桩站封面图片
    @ApiModelProperty(value = "桩站封面图片，必传,最长500位")
    @NotBlank(message = "请删除桩站封面图片")
    @Length(max = 500, message = "桩站封面图片地址长度最长200位")
    private String coverImg;

    //管理公司
    @ApiModelProperty(value = "管理公司，必传,最长100位")
    @NotBlank(message = "请填写管理公司")
    @Length(max = 100, message = "管理公司长度最长100位")
    private String managers;

    //联系方式/服务电话-支持固话和手机号码
    @ApiModelProperty(value = "服务电话，必传,固定电话或者手机号码格式")
    @NotBlank(message = "请填写服务电话")
    @Pattern(regexp = RegExp.CONCAT_PHONE,message = "请输入固定电话或者手机号码")
    private String tel;

    //停车费描述
    @ApiModelProperty(value = "停车费说明")
    @NotBlank(message = "请填写停车费说明")
    @Length(max = 200, message = "停车费说明长度最长200位")
    private String parkingFee;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(String parkingFee) {
        this.parkingFee = parkingFee;
    }
}