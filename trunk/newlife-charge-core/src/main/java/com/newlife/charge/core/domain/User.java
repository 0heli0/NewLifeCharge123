/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 用户表 v1.0
 */


public class User {

    //主键-用户ID
    private Integer id;

    //用户类型(1:车主用户,2:桩站系统主账号,3:桩站系统子账号,4:总后台系统用户)
    private Integer userType;

    //账号状态(1:启用,2:禁用,3:锁定,4:过期)
    private Integer status;

    //登录账号(可能与手机号码一致)
    private String userName;

    //密码
    private String password;

    //盐值
    private String salt;

    //手机号码
    private String mobile;

    //电子邮箱
    private String email;

    //姓名
    private String realName;

    //昵称
    private String nickName;

    //头像
    private String avatarUrl;

    //账户总余额
    //车主用户：余额=(用户账户表)充值金额+优惠充值金额+赠送金额
    //桩站主/子账户：余额=(桩站信息表(桩站资金账户表))桩站余额+未结算金额+冻结金额
    //总后台系统用户：讲道理只会有一个特定账号为总后台系统资金账户，其他总后台账户金额都为0
    private BigDecimal balance;

    //性别(0男，1女)
    private Integer gender;

    //是否验证邮箱(0否，1是)
    private Integer verifyEmail;

    //是否验证手机(0否，1是)
    private Integer verifyMobile;

    //ip地址
    private String ip;

    //创建者
    private String createUser;

    //解锁时间
    private Date liftBanTime;

    //登录时间
    private Date loginTime;

    //注册时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(Integer verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public Integer getVerifyMobile() {
        return verifyMobile;
    }

    public void setVerifyMobile(Integer verifyMobile) {
        this.verifyMobile = verifyMobile;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getLiftBanTime() {
        return liftBanTime;
    }

    public void setLiftBanTime(Date liftBanTime) {
        this.liftBanTime = liftBanTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}