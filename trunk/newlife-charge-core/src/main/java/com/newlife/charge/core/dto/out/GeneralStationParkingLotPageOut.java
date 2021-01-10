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
 * 总后台 桩站车位信息分页查询
 */
@ApiModel
public class GeneralStationParkingLotPageOut {

    //车位Id
    @ApiModelProperty(value = "车位Id")
    private Integer id;

    //车位编号
    @ApiModelProperty(value = "车位编号")
    private String code;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;

    //所属桩站名称
    @ApiModelProperty(value = "所属桩站名称")
    private String stationName;

    //绑定充电桩Id
    @ApiModelProperty(value = "绑定充电桩Id")
    private Integer stationClientId;

    //绑定充电桩编号
    @ApiModelProperty(value = "绑定充电桩编号")
    private String stationClientCode;

    //绑定充电枪编号ID
    @ApiModelProperty(value = "绑定充电枪编号ID")
    private Integer stationClientGunId;

    //绑定充电枪编号
    @ApiModelProperty(value = "绑定充电枪编号")
    private String stationClientGunCode;

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

    public Integer getStationClientId() {
        return stationClientId;
    }

    public void setStationClientId(Integer stationClientId) {
        this.stationClientId = stationClientId;
    }

    public String getStationClientCode() {
        return stationClientCode;
    }

    public void setStationClientCode(String stationClientCode) {
        this.stationClientCode = stationClientCode;
    }

    public Integer getStationClientGunId() {
        return stationClientGunId;
    }

    public void setStationClientGunId(Integer stationClientGunId) {
        this.stationClientGunId = stationClientGunId;
    }

    public String getStationClientGunCode() {
        return stationClientGunCode;
    }

    public void setStationClientGunCode(String stationClientGunCode) {
        this.stationClientGunCode = stationClientGunCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}