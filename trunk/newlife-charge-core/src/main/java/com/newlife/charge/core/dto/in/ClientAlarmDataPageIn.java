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
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 总后台 充电桩故障日志分页查询
 */
@Setter
@Getter
@ApiModel
public class ClientAlarmDataPageIn extends Pageable {

    //桩站id
    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    //告警时间段开始时间
    @ApiModelProperty(value = "告警时间段开始时间")
    private Date startTime;

    //告警时间段结束时间
    @ApiModelProperty(value = "告警时间段结束时间")
    private Date endTime;

}