/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 总后台 账号管理-账号信息
 */
@Setter
@Getter
@ApiModel
public class GeneralUserOut {

    //用户ID
    @ApiModelProperty(value = "账号Id")
    private Integer id;

    //账号状态（1:启用,2:禁用,3:锁定,4:过期）
    @ApiModelProperty(value = "账号状态（1:启用,2:禁用,3:锁定,4:过期）")
    private Integer status;

    //名称，对应用户昵称
    @ApiModelProperty(value = "名称")
    private String nickName;

    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    //姓名
    @ApiModelProperty(value = "姓名")
    private String realName;

    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    //密码
    @ApiModelProperty(value = "密码,密文")
    private String password;

    //角色ID
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

}