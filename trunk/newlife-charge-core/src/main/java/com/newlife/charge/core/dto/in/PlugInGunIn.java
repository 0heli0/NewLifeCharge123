/* -------------------------------------------
 * PlugInGunIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 开始充电参数接收类
 */
@Setter
@Getter
@ApiModel
public class PlugInGunIn {

    @ApiModelProperty(value = "绑定的车位的id")
    @NotNull(message = "绑定的车位的id，不能为空")
    private Integer lotId;

    @ApiModelProperty(value = "充电枪id")
    @NotNull(message = "充电枪id，不能为空")
    private Integer gunId;

    @ApiModelProperty(value = "插枪操作,布尔类型（true表示插枪动作，false表示其他）")
    @NotNull(message = "绑定的车位的id，不能为空")
    private boolean insert;

    @ApiModelProperty(value = "充电枪连接状态(0：未连接,3：已连接)")
    @NotNull(message = "充电枪连接状态，不能为空")
    private Integer status;

}