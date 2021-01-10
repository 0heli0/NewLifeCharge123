package com.newlife.charge.core.dto.in;


import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * 手机号码
 */
@ApiModel
public class MobileIn {

    @ApiModelProperty(value = "手机号,必传，手机号格式")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "手机格式不对")
    private String mobile;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
