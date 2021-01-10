package com.newlife.charge.core.dto.out;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * api 微信登录用户登录态返回信息
 * <p>
 */
@Getter
@Setter
@ApiModel(value = "微信登录用户登录态返回信息")
public class WeiChatLoginStatusOut {

    @ApiModelProperty(value = "用户唯一标识")
    private String openid;

    @ApiModelProperty(value = "数据加解密涉及用户的会话密钥")
    private String sessionKey;

    @ApiModelProperty(value = "错误提示码")
    private String errCode;

    @ApiModelProperty(value = "错误提示信息")
    private String errMsg;

}
