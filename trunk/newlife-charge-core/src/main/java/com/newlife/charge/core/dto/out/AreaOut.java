/**
 * Author: zhengyou
 * Date:   2018/12/11 15:52
 * Descripition:区域输出对象
 */
package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("区域输出信息")
public class AreaOut {

    /**
     * 地区ID
     */
    @ApiModelProperty("地区ID")
    private Integer id;

    /**
     * 地区名字
     */
    @ApiModelProperty("地区名字")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
