/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 总后台 时段电价分组信息展示
 */
@Getter
@Setter
@ApiModel
public class GeneralStationTimePriceGroupOut {

    //时段序号
    @ApiModelProperty(value = "时段序号")
    private String timeNoDesc;

    //时段
    @ApiModelProperty(value = "时段")
    private String time;

    //折扣率(%)
    @ApiModelProperty(value = "折扣率")
    private String rate;

    //电费
    @ApiModelProperty(value = "电费")
    private BigDecimal price;


}