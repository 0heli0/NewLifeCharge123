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
 * RPC充电枪状态更新
 */
@ApiModel
public class RpcStationClientGunStatusUpdateIn {

    //充电桩终端号/充电桩编号
    @ApiModelProperty(value = "充电桩终端号，必传,最长8位")
    @NotBlank(message = "充电桩终端号不能为空")
    @Length(max = 8, message = "充电桩终端号不能为空长度最长8位")
    private String stationClientTerminalNo;

    //充电枪编号
    @ApiModelProperty(value = "充电枪号，必传")
    @NotBlank(message = "充电枪号不能为空")
    private String gunNo;

    //充电枪状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    @ApiModelProperty(value = "充电枪状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)，必传")
    @NotNull(message = "充电枪状态不能为空")
    private Integer status;

    public String getStationClientTerminalNo() {
        return stationClientTerminalNo;
    }

    public void setStationClientTerminalNo(String stationClientTerminalNo) {
        this.stationClientTerminalNo = stationClientTerminalNo;
    }

    public String getGunNo() {
        return gunNo;
    }

    public void setGunNo(String gunNo) {
        this.gunNo = gunNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}