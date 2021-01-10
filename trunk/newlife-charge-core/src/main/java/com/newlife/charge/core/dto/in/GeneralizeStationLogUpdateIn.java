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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@ApiModel(value = "反馈信息更新时参数接收类")
public class GeneralizeStationLogUpdateIn {

    @ApiModelProperty(value = "需要修改的主键id,必填,且大于0")
    @NotNull(message = "id不能为空")
    @Min(value = 1,message = "id必须大于1")
    private Integer id;

    @ApiModelProperty(value = "电话号码,非必填，若有则要进行手机格式验证")
    @Pattern(regexp = RegExp.MOBILE,message = "手机格式不对")
    private String mobile;

    @ApiModelProperty(value = "推广人昵称,限制为20个字数")
    @NotBlank(message = "推广人昵称不能为空")
    @Length(min =1,max = 20,message = "推广人昵称不能小于1位且不能超过20位")
    private String name;

    @ApiModelProperty(value = "预计建站时间")
    private Date buildTime;

    @ApiModelProperty(value = "电站地址,非必填，若有则不能为空")
    @Length(min = 1,message = "电站地址最小字数为1")
    private String stationAddress;

    @ApiModelProperty(value = "电站描述,非必填，若有则不能为空")
    @Length(min = 1,message = "电站描述最小字数为1")
    private String stationDescript;

}
