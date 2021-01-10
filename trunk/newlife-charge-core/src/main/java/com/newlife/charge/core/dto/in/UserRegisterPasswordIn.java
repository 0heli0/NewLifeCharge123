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
public class UserRegisterPasswordIn {

    @ApiModelProperty(value = "手机号,最大长度11位")
    @Length(max = 11, message = "手机号长度不能超过11位")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegExp.MOBILE,message = "手机格式不对")
    private String mobile;

    //密码
    @ApiModelProperty(value = "密码,必传，密码格式")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegExp.PASSWORD, message = "密码格式不对")
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
