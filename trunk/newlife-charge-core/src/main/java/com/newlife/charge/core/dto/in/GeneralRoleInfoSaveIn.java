/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 总后台 角色保存
 */
@ApiModel
public class GeneralRoleInfoSaveIn {


    //角色名称
    @ApiModelProperty(value = "角色名称,必传，最长50位")
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 50,message = "角色名称长度不能超过50位")
    private String roleName;

    //备注
    @ApiModelProperty(value = "备注")
    @Length(max = 100,message = "备注长度不能超过100位")
    private String remark;

    //所有选中的权限
    @ApiModelProperty(value = "所有选中的权限")
    private Integer[] permissionArr;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer[] getPermissionArr() {
        return permissionArr;
    }

    public void setPermissionArr(Integer[] permissionArr) {
        this.permissionArr = permissionArr;
    }
}