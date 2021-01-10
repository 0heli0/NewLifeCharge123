package com.newlife.charge.core.dto.in;

import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 车主用户短信验证码登录
 * <p>
 */
@ApiModel
public class CarUserSmsCodeLoginIn {

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "手机号码格式不对")
    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "短信验证码", required = true)
    @NotBlank(message = "短信验证码不能为空")
    private String code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
