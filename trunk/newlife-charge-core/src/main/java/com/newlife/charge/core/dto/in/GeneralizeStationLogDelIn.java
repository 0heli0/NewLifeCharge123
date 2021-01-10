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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "反馈信息删除参数接收类")
public class GeneralizeStationLogDelIn {

    @ApiModelProperty(required = true, value = "反馈信息id,必填,不能为空,且大于0")
    @NotNull(message = "id不能为空")
    @Min(value = 1,message = "id不能小于1")
    private Integer id;

}
