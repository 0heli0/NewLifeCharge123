/**
 * Author: zhengyou
 * Date:   2018/12/11 15:54
 * Descripition:区域接收对象
 */
package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 总后台站内信分页查询
 */
@ApiModel
public class GeneralNoticePageIn extends Pageable {
    /**
     * 站内信标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
