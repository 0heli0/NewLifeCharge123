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
 * 总后台 桩站资质审核
 */
@ApiModel
public class GeneralStationUserAuditIn {

    //公司ID
    @ApiModelProperty(value = "公司ID")
    @NotNull(message = "待审核公司ID不能为空")
    private Integer companyId;

    //回复语
    @ApiModelProperty(value = "回复语,最长200位")
    @Length(max = 200, message = "回复语最大长度不能超过200位")
    private String remark;

    //抽佣比例
    @ApiModelProperty(value = "抽佣比例,最大100")
    @Max(value = 100, message = "抽佣比例最大为100")
    @Min(value = 0, message = "抽佣比例最小为0")
    private Integer commissionRation;


    //审核结果(1:审核 2:驳回)
    @ApiModelProperty(value = "审核结果(1:审核 2:驳回)")
    @NotNull(message = "请选择审核结果")
    private Integer auditStatus;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCommissionRation() {
        return commissionRation;
    }

    public void setCommissionRation(Integer commissionRation) {
        this.commissionRation = commissionRation;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}