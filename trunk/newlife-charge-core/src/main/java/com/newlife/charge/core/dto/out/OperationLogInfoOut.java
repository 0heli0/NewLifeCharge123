package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 操作日志分页列表数据
 */
@Getter
@Setter
@ApiModel
public class OperationLogInfoOut {

    // 主键id
    @ApiModelProperty(value = "主键id")
    private Integer id;

    //用户名
    @ApiModelProperty(value = "用户名")
    private String loginName;

    //操作类型
    @ApiModelProperty(value = "操作类型")
    private Integer operationType;


    //操作类型名称
    @ApiModelProperty(value = "操作类型名称")
    private String operationTypeName;

    //操作内容
    @ApiModelProperty(value = "操作内容")
    private String operationText;

    //操作/动作
    @ApiModelProperty(value = "动作")
    private String action;

    //操作模块
    @ApiModelProperty(value = "操作模块")
    private String operationMoudle;

    //IP地址
    @ApiModelProperty(value = "IP地址")
    private String ip;

    //请求的网址
    @ApiModelProperty(value = "请求的网址")
    private String requestUrl;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
