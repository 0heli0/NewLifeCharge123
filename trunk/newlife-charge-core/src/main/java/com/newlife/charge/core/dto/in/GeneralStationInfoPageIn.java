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
 * 总后台 桩站管理分页查询
 */
@ApiModel
public class GeneralStationInfoPageIn extends Pageable {


    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}