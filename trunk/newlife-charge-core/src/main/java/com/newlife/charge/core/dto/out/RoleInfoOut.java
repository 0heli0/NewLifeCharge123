/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 角色
 */
@ApiModel(value = "角色信息")
public class RoleInfoOut {

    //角色ID
    @ApiModelProperty(value = "角色ID")
    private Integer id;

    //角色名
    @ApiModelProperty(value = "角色名")
    private String roleName;

    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

    //所有父级权限
    @ApiModelProperty(value = "所有父级权限")
    private List<PermissionInfoOut> parents= Lists.newArrayList();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<PermissionInfoOut> getParents() {
        return parents;
    }

    public void setParents(List<PermissionInfoOut> parents) {
        this.parents = parents;
    }
}