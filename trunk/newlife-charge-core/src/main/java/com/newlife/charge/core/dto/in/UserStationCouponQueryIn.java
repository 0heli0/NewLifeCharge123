/* -------------------------------------------
 * UserCouponQueryIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * 在指定桩站中用户查看可领取优惠券接收类
 */

@Getter
@Setter
@ApiModel(value = "在指定桩站中用户查看可领取优惠券接收类")
public class UserStationCouponQueryIn extends Pageable{

    @ApiModelProperty(value = "当前的桩站id，非空")
    @NotNull(message = "桩站id不能为空")
    private Integer stationId;

}