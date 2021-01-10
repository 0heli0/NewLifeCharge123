/* -------------------------------------------
 * PreCharge.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 桩站 优惠劵查询
 */

@Getter
@Setter
@ApiModel(value = "分页查询优惠券接收类")
public class CouponStationQueryIn extends Pageable{


    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    @ApiModelProperty(value = "优惠劵类型")
    private Integer status;

}