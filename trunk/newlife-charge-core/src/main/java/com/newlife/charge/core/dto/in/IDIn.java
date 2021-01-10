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

/**
 * 通用主键接收类，可空
 */
@Setter
@Getter
@ApiModel
public class IDIn {

    //主键ID
    @ApiModelProperty(value = "主键ID")
    private Integer id;

}