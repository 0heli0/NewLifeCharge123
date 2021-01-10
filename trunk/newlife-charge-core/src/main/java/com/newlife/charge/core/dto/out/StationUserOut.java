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
 * 桩站后台 账号管理-账号信息
 */
@ApiModel
public class StationUserOut {

    //用户ID
    @ApiModelProperty(value = "账号Id")
    private Integer id;


    //名称，对应用户
    @ApiModelProperty(value = "名称")
    private String userName;

    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;


    //姓名
    @ApiModelProperty(value = "姓名")
    private String realName;


    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    //角色名称
    @ApiModelProperty(value = "角色名称")
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}