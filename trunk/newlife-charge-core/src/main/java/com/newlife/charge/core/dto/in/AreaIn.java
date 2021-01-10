/**
 * Author: zhengyou
 * Date:   2018/12/11 15:54
 * Descripition:区域接收对象
 */
package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class AreaIn {
    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID,为空或null则查询所有level_type=1的区域（顶级区域）")
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
