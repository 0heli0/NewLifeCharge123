/**
 * Author: zhengyou
 * Date:   2018/12/11 15:54
 * Descripition:区域接收对象
 */
package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车主用户站内信阅读
 */
@ApiModel
public class CarNoticeReadIn {
    /**
     * 站内信ID
     */
    @ApiModelProperty(value = "站内信ID")
    private Integer noticeId;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }
}
