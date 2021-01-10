/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 总后台账号更新
 */
@ApiModel
public class GeneralUserUpdateIn {

    //用户ID
    @ApiModelProperty(value = "账号Id")
    @NotNull(message = "账号Id不能空")
    private Integer id;


    //名称，对应用户昵称
    @ApiModelProperty(value = "名称,必传，最大长度50位")
    @NotBlank(message = "名称不能为空")
    @Length(max = 50, message = "名称长度不能超过50位")
    private String nickName;

    //手机号码
    @ApiModelProperty(value = "账号（手机号码）,必传，手机号格式")
    @NotBlank(message = "账号(手机号码)不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "手机号码格式不对")
    private String mobile;

    //姓名
    @ApiModelProperty(value = "姓名,必传，最大长度50位")
    @NotBlank(message = "姓名不能为空")
    @Length(max = 50, message = "姓名长度不能超过50位")
    private String realName;

    //密码
    @ApiModelProperty(value = "密码,非必填,如果传空则表示不修改密码")
    private String password;
    //角色id
    @ApiModelProperty(value = "角色id，必传")
    @NotNull(message = "请选择角色")
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}