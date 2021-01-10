/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 桩站后台账号分页查询
 */
@ApiModel
public class StationUserPageQuery extends Pageable {


    //账号名称，对应用户昵称
    @ApiModelProperty(value = "账号名称,最大长度50位")
    @Length(max = 50, message = "账号名称长度不能超过50位")
    private String userName;

    //真实姓名
    @ApiModelProperty(value = "真实姓名,最大长度50位")
    @Length(max = 50, message = "真实姓名长度不能超过50位")
    private String realName;

    //账号类型
    @ApiModelProperty(value = "账号类型")
    private Integer userType;

    //桩站id
    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    //账号类型 数组
    @ApiModelProperty(value = "账号类型数组")
    private Integer[] userTypes;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer[] getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(Integer[] userTypes) {
        this.userTypes = userTypes;
    }
}