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
@ApiModel(value = "操作日志详情接收类")
public class OperationLogDetailIn {

    @ApiModelProperty(value = "主键-操作日志id,必填,不能小于1")
    @NotNull
    @Min(value = 1,message = "id不能小于1")
    private Integer id;

}
