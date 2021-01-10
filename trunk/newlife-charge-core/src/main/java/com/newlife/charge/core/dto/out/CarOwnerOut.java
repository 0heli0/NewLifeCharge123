/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.charge.core.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 车主信息 v1.0
 */

@Setter
@Getter
@ApiModel(value = "车主信息")
public class CarOwnerOut {

    //主键-用户ID
    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "用户唯一标识id")
    private String openid;

    //账号状态(1:启用,2:禁用,3:锁定,4:过期)
    @ApiModelProperty(value = "账号状态(1:启用,2:禁用,3:锁定,4:过期)")
    private Integer status;

    //登录账号(可能与手机号码一致)
    @ApiModelProperty(value = "登录账号")
    private String userName;

    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    //电子邮箱
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    //姓名
    @ApiModelProperty(value = "姓名")
    private String realName;

    //昵称
    @ApiModelProperty(value = "昵称")
    private String nickName;

    //头像
    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    //账户总余额
    //车主用户：余额=(用户账户表)充值金额+优惠充值金额+赠送金额
    //桩站主/子账户：余额=(桩站信息表(桩站资金账户表))桩站余额+未结算金额+冻结金额
    //总后台系统用户：讲道理只会有一个特定账号为总后台系统资金账户，其他总后台账户金额都为0
    @ApiModelProperty(value = "账户总余额")
    private BigDecimal balance;

    //性别(0男，1女)
    @ApiModelProperty(value = "性别(0男，1女，3保密)")
    private Integer gender;

    @ApiModelProperty(value = "性别(男:0，女:1，保密:3),默认为保密")
    private String genderName;

    @ApiModelProperty(value = "新的站内信条数")
    private Integer noticeCount;

    //是否验证邮箱(0否，1是)
    @ApiModelProperty(value = "是否验证邮箱(0否，1是)")
    private Integer verifyEmail;

    //是否验证手机(0否，1是)
    @ApiModelProperty(value = "是否验证手机(0否，1是)")
    private Integer verifyMobile;

    //ip地址
    @ApiModelProperty(value = "ip地址")
    private String ip;

    //创建者
    @ApiModelProperty(value = "创建者")
    private String createUser;

    //解锁时间
    @ApiModelProperty(value = "解锁时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date liftBanTime;

    @ApiModelProperty(value = "解锁时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date liftBanTimestamp;

    //登录时间
    @ApiModelProperty(value = "登录时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;

    @ApiModelProperty(value = "登录时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date loginTimestamp;

    //注册时间
    @ApiModelProperty(value = "注册时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "注册时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public Date getLiftBanTimestamp() {
        if(liftBanTime != null){
            liftBanTimestamp = liftBanTime;
        }
        return liftBanTimestamp;
    }

    public Date getLoginTimestamp() {
        if(loginTime != null){
            loginTimestamp = loginTime;
        }
        return loginTimestamp;
    }

    public Date getCreateTimestamp() {
        if(createTime != null){
            createTimestamp = createTime;
        }
        return createTimestamp;
    }

    public String getGenderName() {

        if(gender == null){
            genderName = GenderEnum.DEFAULT.getDescription();

        }else if (gender == GenderEnum.MAN.getValue()){
            genderName = GenderEnum.MAN.getDescription();

        }else if (gender == GenderEnum.WOMAN.getValue()){
            genderName = GenderEnum.WOMAN.getDescription();

        }else {
            genderName = GenderEnum.DEFAULT.getDescription();
        }
        return genderName;
    }
}