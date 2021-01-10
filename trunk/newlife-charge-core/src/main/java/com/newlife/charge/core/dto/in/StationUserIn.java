/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.Reg.RegExp;
import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 桩站后台子账号保存 接收类
 */
@ApiModel
public class StationUserIn extends Pageable {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    //账号名称，对应用户昵称
    @ApiModelProperty(value = "账号名称,最大长度11位")
    @Pattern(regexp = RegExp.MOBILE, message = "手机格式不对")
    @NotNull(message = "账号名称")
    private String userName;

    //账号名称，对应用户昵称
    @ApiModelProperty(value = "账号名称,最大长度50位")
    @Length(max = 50, message = "账号名称长度不能超过50位")
    private String realName;

    //角色id
    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色id")
    private Integer roleId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}