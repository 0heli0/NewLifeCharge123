/**
 * Author: zhengyou
 * Date:   2019/1/3 17:09
 * Descripition:
 */
package com.newlife.charge.core.dto.out;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel(value = "用户反馈信息")
public class SuggestionOut {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户电话号码")
    private String mobile;

    @ApiModelProperty(value = "反馈意见内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public Date getCreateTimestamp() {
        if(createTime != null){
            this.createTimestamp = this.createTime;
        }
        return createTimestamp;
    }
}
