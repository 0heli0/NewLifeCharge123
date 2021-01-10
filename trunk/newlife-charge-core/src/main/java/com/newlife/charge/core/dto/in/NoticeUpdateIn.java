/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 站内信 更新
 */
@ApiModel
public class NoticeUpdateIn {

    //ID
    @ApiModelProperty(value = "主键ID")
    @NotNull(message = "主键ID不能为空")
    private Integer id;

    //标题
    @ApiModelProperty(value = "标题，必传,最长100位")
    @NotBlank(message = "标题不能为空")
    @Length(max = 100, message = "标题长度最长100位")
    private String title;

    //内容
    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}