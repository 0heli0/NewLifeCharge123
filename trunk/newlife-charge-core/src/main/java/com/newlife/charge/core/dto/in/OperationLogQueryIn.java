/**
 * Author: zhengyou
 * Date:   2019/1/3 17:09
 * Descripition:
 */
package com.newlife.charge.core.dto.in;


import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 操作日志查询参数接收类
 */
@Getter
@Setter
@ApiModel
public class OperationLogQueryIn extends Pageable {

    @ApiModelProperty(name = "userName", value = "登录名,不能超过30位,")
    @Length(max = 30, message = "登录名不能超过30个位")
    private String userName;


    @ApiModelProperty(value = "操作类型{0:登录操作,1:业务操作,空查询全部}")
    private Integer operationType;

    @ApiModelProperty(value = "IP地址,长度不能超过20位")
    @Length(max = 20, message = "ip长度不能超过20位")
    private String ip;

    @ApiModelProperty(value = "查询日志开始时间")
    private Date startTime;

    @ApiModelProperty(value = "查询日志结束时间")
    private Date endTime;

}
