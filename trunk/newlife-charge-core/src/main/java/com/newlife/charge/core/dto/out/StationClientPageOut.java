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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * 总后台 桩站充电桩信息分页查询信息
 */
@ApiModel
public class StationClientPageOut {

    //充电桩ID
    @ApiModelProperty(value = "充电桩ID")
    private Integer id;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String code;

    //品牌
    @ApiModelProperty(value = "品牌")
    private String brand;

    //单双枪类型(1:单枪，2:双枪)
    @ApiModelProperty(value = "单双枪类型(1:单枪，2:双枪)")
    private Integer gunType;

    //单双枪类型名称
    @ApiModelProperty(value = "单双枪类型名称")
    private String gunTypeName;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;

    //所属桩站名称
    @ApiModelProperty(value = "所属桩站名称")
    private String stationName;


    //绑定车位编号(多个用逗号隔开)
    @ApiModelProperty(value = "绑定车位编号(多个用逗号隔开)")
    private String stationParkingLotCodes;

    //绑定充电枪编号(多个用逗号隔开)
    @ApiModelProperty(value = "绑定充电枪编号(多个用逗号隔开)")
    private String stationClientGunCodes;


    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getGunType() {
        return gunType;
    }

    public void setGunType(Integer gunType) {
        this.gunType = gunType;
    }

    public String getGunTypeName() {
        return gunTypeName;
    }

    public void setGunTypeName(String gunTypeName) {
        this.gunTypeName = gunTypeName;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationParkingLotCodes() {
        return stationParkingLotCodes;
    }

    public void setStationParkingLotCodes(String stationParkingLotCodes) {
        this.stationParkingLotCodes = stationParkingLotCodes;
    }

    public String getStationClientGunCodes() {
        return stationClientGunCodes;
    }

    public void setStationClientGunCodes(String stationClientGunCodes) {
        this.stationClientGunCodes = stationClientGunCodes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}