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
 * 总后台 桩站账号管理分页查询（主账号）
 */
@ApiModel
public class GeneralStationUserPageIn extends Pageable {


    //公司名称
    @ApiModelProperty(value = "公司名称,最长100位")
    @Length(max = 100, message = "公司名称最大长度不能超过100位")
    private String companyName;

    //账号（手机号）
    @ApiModelProperty(value = "账号（手机号）,最长11位")
    @Length(max = 11, message = "账号（手机号）最大长度不能超过11位")
    private String mobile;

    //管理员姓名
    @ApiModelProperty(value = "管理员姓名,最长50位")
    @Length(max = 50, message = "管理员姓名最大长度不能超过50位")
    private String managerName;


    //审核状态(1审核中 2审核通过 3审核不通过，空表示全部)
    @ApiModelProperty(value = "审核状态(1审核中 2审核通过 3审核不通过，空表示全部)")
    private Integer auditStatus;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}