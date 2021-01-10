/* -------------------------------------------
 * WeiChatLoginIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * 微信登录时传入加密信息 v1.0
 */

@Setter
@Getter
@ApiModel(value = "微信登录时传入加密信息")
public class WeiChatLoginIn {


    @ApiModelProperty(value = "用户唯一标识")
    @NotNull(message = "用户唯一标识不能为空")
    private String openId;

    @ApiModelProperty(value = "数据加解密涉及用户的会话密钥")
    @NotNull(message = "对称解密算法初始向量不能为空")
    private String sessionId;

    @ApiModelProperty(value = "接口返回的加密数据")
    @NotNull(message = "接口返回的加密数据不能为空")
    private String encryptedData;

    @ApiModelProperty(value = "对称解密算法初始向量")
    @NotNull(message = "对称解密算法初始向量不能为空")
    private String iv;
}