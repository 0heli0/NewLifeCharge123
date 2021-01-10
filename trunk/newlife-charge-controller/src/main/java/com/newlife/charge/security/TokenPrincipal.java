package com.newlife.charge.security;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.core.domain.exModel.UserEx;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录 的LoginToken
 * <p>
 */
public class TokenPrincipal {

    //用户主键id
    private Integer userId;
    //用户类型(1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户)
    private Integer userType;
    //账号状态(1:启用,2:禁用,3:锁定,4:过期)
    private Integer status;
    //登录账号/用户名(可能与手机号码一致)
    private String userName;
    //姓名
    private String realName;
    //昵称
    private String nickName;
    //手机号码
    private String mobile;
    //桩站Id
    private Integer companyInfoId;
    //桩站Id
    private Integer stationId;
    //角色id
    private Integer roleId;
    //登录时间
    private String loginTime;
    //解锁时间
    private String liftBanTime;
    private Integer auditStatus;//桩站公司审核状态
    private List<String> permissionStrs; //账号所有角色权限


    public TokenPrincipal() {

    }

    public TokenPrincipal(UserEx userEx, List<String> permissionStrs) {
        this.userId = userEx.getId();
        this.userType = userEx.getUserType();
        this.status = userEx.getStatus();
        this.userName = userEx.getUserName();
        this.realName = userEx.getRealName();
        this.nickName = userEx.getNickName();
        this.mobile = userEx.getMobile();
        this.stationId = userEx.getStationId();
        this.companyInfoId = userEx.getCompanyInfoId();
        this.roleId = userEx.getRoleId();
        if (userEx.getLoginTime() != null) {
            this.loginTime = DateUtils.formatDate(userEx.getLoginTime(), "yyyy-MM-dd HH:mm:ss");
        }
        if (userEx.getLiftBanTime() != null) {
            this.liftBanTime = DateUtils.formatDate(userEx.getLiftBanTime(), "yyyy-MM-dd HH:mm:ss");
        }
        this.auditStatus = userEx.getAuditStatus();
        this.permissionStrs = permissionStrs;
        if (Collections3.isEmpty(this.permissionStrs)) {
            this.permissionStrs = Lists.newArrayList();
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("userId", this.userId);
        claims.put("userType", this.userType);
        claims.put("status", this.status);
        claims.put("userName", this.userName);
        claims.put("realName", this.realName);
        claims.put("nickName", this.nickName);
        claims.put("mobile", this.mobile);
        claims.put("stationId", this.stationId);
        claims.put("companyInfoId", this.companyInfoId);
        claims.put("roleId", this.roleId);
        claims.put("loginTime", this.loginTime);
        claims.put("liftBanTime", this.liftBanTime);
        claims.put("auditStatus", this.auditStatus);
        claims.put("permissionStrs", JSON.toJSONString(this.permissionStrs));

        return claims;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLiftBanTime() {
        return liftBanTime;
    }

    public void setLiftBanTime(String liftBanTime) {
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

    public Integer getCompanyInfoId() {
        return companyInfoId;
    }

    public void setCompanyInfoId(Integer companyInfoId) {
        this.companyInfoId = companyInfoId;
    }
}
