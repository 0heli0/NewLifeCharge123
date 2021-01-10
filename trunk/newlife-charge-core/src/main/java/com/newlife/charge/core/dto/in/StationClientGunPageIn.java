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

/**
 * 总后台 充电枪分页查询
 */
@Setter
@Getter
@ApiModel
public class StationClientGunPageIn extends Pageable {


    //充电枪编号
    @ApiModelProperty(value = "充电枪编号")
    private String code;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //车位编号
    @ApiModelProperty(value = "车位编号")
    private String stationParkingLotCode;

    //终端状态(空:全部,1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    @ApiModelProperty(value = "状态(空字符串:全部,1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)")
    private Integer status;

    //桩站id
    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

}