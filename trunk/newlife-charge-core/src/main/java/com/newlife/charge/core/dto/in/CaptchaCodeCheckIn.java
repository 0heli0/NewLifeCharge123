/**
 * Author: zhengyou
 * Date:   2018/12/18 10:38
 * Descripition:验证图形验证码输入
 */
package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 图形验证码 验证输入
 */
@ApiModel(value = "图形验证码 验证输入")
public class CaptchaCodeCheckIn {

    /**
     * sessionId
     */
    @NotBlank(message = "sessionId不能为空")
    @ApiModelProperty(value = "sessionId")
    private String sessionId;

    /**
     * 图形验证码
     */
    @NotBlank(message = "图形验证码不能为空")
    @ApiModelProperty(value = "图形验证码",example = "7xue")
    private String checkCaptchaCode;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCheckCaptchaCode() {
        return checkCaptchaCode;
    }

    public void setCheckCaptchaCode(String checkCaptchaCode) {
        this.checkCaptchaCode = checkCaptchaCode;
    }
}
