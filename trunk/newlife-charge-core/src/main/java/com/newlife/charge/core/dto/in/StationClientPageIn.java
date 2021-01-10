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
 * 总后台 充电桩分页查询
 */
@ApiModel
public class StationClientPageIn extends Pageable {


    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String code;

    //单双枪类型
    @ApiModelProperty(value = "单双枪类型(空：全部，1：单枪，2：双枪)")
    private Integer gunType;

    public Integer getGunType() {
        return gunType;
    }

    public void setGunType(Integer gunType) {
        this.gunType = gunType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}