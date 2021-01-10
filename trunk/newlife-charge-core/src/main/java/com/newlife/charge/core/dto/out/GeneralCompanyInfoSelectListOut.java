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
 * 总后台 公司信息下拉列表
 */
@ApiModel
public class GeneralCompanyInfoSelectListOut {

    //公司ID
    @ApiModelProperty(value = "公司ID")
    private Integer id;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}