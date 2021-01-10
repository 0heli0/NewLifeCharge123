/**
 * Author: zhengyou
 * Date:   2019/1/3 17:09
 * Descripition:
 */
package com.newlife.charge.core.dto.in;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(value = "反馈信息批量删除参数接收类")
public class SuggestionDelsIn {

    @ApiModelProperty(required = true, value = "需要删除的反馈id数组,必填,长度不能为0,元素格式为数字")
    @Size(min = 1,message = "传入id参数不能为空")
    private int[] ids;

}
