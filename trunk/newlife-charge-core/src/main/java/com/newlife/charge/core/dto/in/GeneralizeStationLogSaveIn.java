/**
 * Author: zhengyou
 * Date:   2019/1/3 17:09
 * Descripition:
 */
package com.newlife.charge.core.dto.in;


import com.newlife.charge.common.Reg.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 建站推广人新增参数接收类
 */
@Getter
@Setter
@ApiModel
public class GeneralizeStationLogSaveIn {

    @ApiModelProperty(required = true, value = "推广人电话,必填,必须按照手机格式")
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = RegExp.MOBILE, message = "手机号码格式不对")
    private String mobile;

    @ApiModelProperty(value = "推广人昵称,非必填,最大不能超过20位")
    @Length(max = 20, message = "推广人昵称不能超过20位")
    private String name;

    @ApiModelProperty(value = "预计建站时间,非必填，格式:yyyy-MM-dd")
    private String buildTime;

    @ApiModelProperty(value = "电站地址,必填,不得超过50位")
    @Length(max = 50, message = "电站地址不能超过50个字符")
    private String stationAddress;

    @ApiModelProperty(value = "电站描述,非必填,长度不能超过100")
    @Length(max = 100, message = "电站描述字数不能超过100")
    @NotBlank(message = "电站描述不能为空")
    private String stationDescript;


}
