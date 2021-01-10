/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 用户待更新的信息 v1.0
 */

@Setter
@Getter
@ApiModel(value = "用户待更新的信息")
public class CarOwnerUpdateIn {


    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    //电子邮箱
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    //昵称
    @ApiModelProperty(value = "昵称")
    private String nickName;

    //头像
    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    //性别(0男，1女)
    @ApiModelProperty(value = "性别(0男，1女)")
    private Integer gender;
}