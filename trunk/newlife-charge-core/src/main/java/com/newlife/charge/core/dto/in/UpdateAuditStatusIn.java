/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 更新审核状态
 */
@ApiModel
public class UpdateAuditStatusIn {

    //ID
    @NotNull(message = "主键ID不能为空")
    @ApiModelProperty(value = "主键ID,必传")
    private Integer id;

    //审核状态
    @NotNull(message = "审核状态不能为空")
    @ApiModelProperty(value = "审核状态,必传：1-审核中，2-审核通过，3-审核失败")
    private Integer auditStatus;

    //审核时间
    @NotNull(message = "审核时间不能为空")
    @ApiModelProperty(value = "审核时间，必传")
    private Date auditTime;

    //备注
    @NotBlank(message = "备注不能为空")
    @Length(max = 300,message = "备注长度不能超过300位")
    @ApiModelProperty(value = "备注，长度不超过300位")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}