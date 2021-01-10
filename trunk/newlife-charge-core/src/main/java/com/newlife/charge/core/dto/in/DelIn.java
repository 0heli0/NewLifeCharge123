package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 公用单条删除接收类
 */
@ApiModel
public class DelIn {

    @ApiModelProperty(value = "需要单条删除的主键Id")
    @NotNull(message = "需要单条删除的主键Id不能空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
