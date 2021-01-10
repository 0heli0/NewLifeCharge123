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
import org.hibernate.validator.constraints.Length;

/**
 * 总后台 用户管理-分页查询（车主用户）
 */
@ApiModel
public class GeneralCarUserPageIn extends Pageable {


    //手机号
    @ApiModelProperty(value = "手机号,最大长度11位")
    @Length(max = 11, message = "手机号长度不能超过11位")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}