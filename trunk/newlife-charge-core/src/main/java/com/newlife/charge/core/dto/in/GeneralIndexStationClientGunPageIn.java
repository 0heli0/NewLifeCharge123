/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 总后台首页充电枪状态分页查询
 */
@ApiModel
public class GeneralIndexStationClientGunPageIn extends Pageable {


    //桩站ID
    @ApiModelProperty(value = "桩站ID")
    private Integer stationId;
    //终端状态(空:全部,1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    @ApiModelProperty(value = "状态(空:全部,1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private Integer status;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}