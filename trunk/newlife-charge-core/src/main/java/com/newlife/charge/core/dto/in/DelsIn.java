package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * 公用批量删除接收类
 */
@ApiModel
public class DelsIn {

    @ApiModelProperty(value = "需要批量删除的记录的主键ID数组")
    @Size(min = 1,message = "需要批量删除的记录的主键ID数组不能为空")
    private int[] ids;

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }
}
