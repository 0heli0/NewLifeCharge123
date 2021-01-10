package com.newlife.charge.core.dto.out;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * api 登录用户输出信息
 * <p>
 */
@ApiModel
public class UserLoginOut {

    //授权的token
    @ApiModelProperty(value = "授权token")
    private String token;

    //用户类型(1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户)
    @ApiModelProperty(value = "用户类型 1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户")
    private Integer userType;
    //账号状态(1:启用,2:禁用,3:锁定,4:过期)
    @ApiModelProperty(value = "账号状态(1:启用,2:禁用,3:锁定,4:过期)")
    private Integer status;

    @ApiModelProperty(value = "用户名")
    private String userName;//用户名

    @ApiModelProperty(value = "真实姓名")
    private String realName;//真实姓名

    @ApiModelProperty(value = "昵称")
    private String nickName;//昵称

    @ApiModelProperty(value = "手机号")
    private String mobile;//手机号

    @ApiModelProperty(value = "桩站ID，可能为空")
    private Integer stationId;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "登录时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;//登录时间

    @ApiModelProperty(value = "解锁时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liftBanTime;//解锁时间

    @ApiModelProperty(value = "桩站公司审核状态：null-非桩站用户，1-审核中，2-审核通过，3-审核失败")
    private Integer auditStatus;//代理商审核情况

    @ApiModelProperty(value = "用户的权限")
    private List<String> permissionStrs; //用户的权限

    @ApiModelProperty(value = "桩站用户当前手机号拥有的可以切换的账号个数，包含自身，所以起码应该有一个")
    private Integer switchToStationCounts ;

    @ApiModelProperty(value = "公司ID，可能为空")
    private Integer companyInfoId;

    //是否有多个桩站
    private boolean hasMoreStation;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLiftBanTime() {
        return liftBanTime;
    }

    public void setLiftBanTime(Date liftBanTime) {
        this.liftBanTime = liftBanTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public List<String> getPermissionStrs() {
        return permissionStrs;
    }

    public void setPermissionStrs(List<String> permissionStrs) {
        this.permissionStrs = permissionStrs;
    }

    public Integer getSwitchToStationCounts() {
        return switchToStationCounts;
    }

    public void setSwitchToStationCounts(Integer switchToStationCounts) {
        this.switchToStationCounts = switchToStationCounts;
    }

    public Integer getCompanyInfoId() {
        return companyInfoId;
    }

    public void setCompanyInfoId(Integer companyInfoId) {
        this.companyInfoId = companyInfoId;
    }

    public boolean isHasMoreStation() {
        return hasMoreStation;
    }

    public void setHasMoreStation(boolean hasMoreStation) {
        this.hasMoreStation = hasMoreStation;
    }
}
