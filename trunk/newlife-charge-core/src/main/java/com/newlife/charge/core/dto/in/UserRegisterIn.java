package com.newlife.charge.core.dto.in;



import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 桩站用户注册
 * @Author: Linzq
 * @CreateDate:  2019/5/5 0005 10:39
 */
public class UserRegisterIn {

    @ApiModelProperty(value = "手机号,最大长度11位")
    @Length(max = 11, message = "手机号长度不能超过11位")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegExp.MOBILE,message = "手机格式不对")
    private String mobile;

    @ApiModelProperty(value = "短信验证码不能为空")
    @NotBlank(message = "短信验证码不能为空")
    private String msgCode;

    @ApiModelProperty(value = "验证码类型不能为空")
    @NotBlank(message = "验证码类型不能为空")
    private String smsType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
