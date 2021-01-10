package com.newlife.charge.core.dto.in;



import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserInPassDto {

    @ApiModelProperty(value = "密码,必传，密码格式")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegExp.PASSWORD, message = "密码格式不对")
    private String password;

    @ApiModelProperty(value = "密码,必传，密码格式")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegExp.PASSWORD, message = "密码格式不对")
    private String newPassword;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
