/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 公用下拉框数据模型
 */
@ApiModel
public class SelectListOut {

    //ID
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    //名称
    @ApiModelProperty(value = "名称")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelectListOut() {
    }

    public SelectListOut(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}