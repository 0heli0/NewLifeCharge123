/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 桩站车位 更新
 */
@ApiModel
public class StationParkingLotUpdateIn {


    //车位ID
    @ApiModelProperty(value = "车位ID，必传")
    @NotNull(message = "请选择需要修改的记录")
    private Integer id;

    //车位编号
    @ApiModelProperty(value = "车位编号，必传,最长50位")
    @NotBlank(message = "请填写车位编号")
    @Length(max = 50, message = "车位编号长度最长50位")
    private String code;

    //桩站ID
    @ApiModelProperty(value = "桩站ID，必传")
    @NotNull(message = "请选择所属桩站")
    private Integer stationId;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}