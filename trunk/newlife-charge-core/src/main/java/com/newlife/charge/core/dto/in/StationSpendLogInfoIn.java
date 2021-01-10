/* -------------------------------------------
 * Role.java
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
 * 总后台-桩站资金统计详情接收类
 */
@Setter
@Getter
@ApiModel
public class StationSpendLogInfoIn {

    //主键ID
    @ApiModelProperty(value = "主键ID")
    @NotNull(message = "主键ID不能为空")
    private Integer id;

    //类型（4.卖电账单，41.卖电流水）
    @ApiModelProperty(value = "类型（4.卖电账单，41.卖电流水）")
    @NotNull(message = "类型不能为空")
    private Integer type;

}