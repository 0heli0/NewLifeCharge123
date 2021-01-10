package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * api 登录接收类
 * <p>
 */
@ApiModel(value = "登录接收类")
public class UserLoginIn {

    @NotBlank(message = "用户名(手机号码）不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "用户名(手机号码）格式不对")
    @ApiModelProperty(value = "用户名(手机号码）",required = true)
    private String userName;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "图形验证码",required = true)
    @NotBlank(message = "图形验证码不能为空")
    private String captchaCode;

    @ApiModelProperty(value = "与图形验证码捆绑的sessionId", required = true)
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
