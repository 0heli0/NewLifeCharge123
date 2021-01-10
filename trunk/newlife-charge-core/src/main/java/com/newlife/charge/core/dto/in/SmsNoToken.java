package com.newlife.charge.core.dto.in;


import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 发送短信
 * <p>
 */
@ApiModel(value = "发送短信")
public class SmsNoToken {

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "手机格式不对")
    private String mobile;

    @ApiModelProperty(value = "发送类型,如下：carMobileLogin-车主用户短信验证码登录,userRegister-桩站账号注册," +
            "forgetPwd-忘记密码,msgLogin-验证码登入")
    private String smsType;// 发送类型

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
