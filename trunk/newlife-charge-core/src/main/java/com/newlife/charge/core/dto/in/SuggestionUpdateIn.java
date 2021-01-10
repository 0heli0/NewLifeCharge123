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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ApiModel(value = "反馈信息更新参数接收类")
public class SuggestionUpdateIn {

    @ApiModelProperty(value = "主键id,必填,数字且大于0")
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Integer id;


    @ApiModelProperty(value = "电话号码,非必填，若有则要进行手机格式验证")
    @Pattern(regexp = RegExp.MOBILE,message = "手机格式不对")
    private String mobile;

    @ApiModelProperty(value = "反馈内容,不能为空且不大于300个字符")
    @NotBlank(message = "反馈内容不能为空")
    @Length(max = 300,message = "反馈内容字数超过300")
    private String content;
}
