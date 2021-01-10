/**
 * Author: zhengyou
 * Date:   2019/1/3 17:09
 * Descripition:
 */
package com.newlife.charge.core.dto.in;


import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ApiModel(value = "反馈信息添加参数接收类")
public class SuggestionSaveIn {

    @ApiModelProperty(required = true, value = "电话号码,必填,必须按照手机格式")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "手机格式不对")
    private String mobile;

    @ApiModelProperty(value = "反馈内容,必填,长度不能超过300")
    @NotBlank(message = "反馈内容不能为空")
    @Length(max = 300,message = "反馈内容字数超过300")
    private String content;

}
