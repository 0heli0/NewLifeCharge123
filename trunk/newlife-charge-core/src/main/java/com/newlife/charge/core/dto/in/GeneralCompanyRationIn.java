/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 总后台 设置抽佣比例
 */
@ApiModel
public class GeneralCompanyRationIn {

    //公司ID
    @ApiModelProperty(value = "公司ID")
    @NotNull(message = "公司ID不能为空")
    private Integer companyId;

    //抽佣比例
    @ApiModelProperty(value = "抽佣比例,最小0最大100")
    @NotNull(message = "抽佣比例不能为空")
    @Max(value = 100, message = "抽佣比例最大为100")
    @Min(value = 0, message = "抽佣比例最小为0")
    private Integer commissionRation;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCommissionRation() {
        return commissionRation;
    }

    public void setCommissionRation(Integer commissionRation) {
        this.commissionRation = commissionRation;
    }
}