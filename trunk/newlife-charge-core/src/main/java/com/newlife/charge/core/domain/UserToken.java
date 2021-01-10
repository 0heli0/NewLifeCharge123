/* -------------------------------------------
 * UserToken.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 用户Token表 v1.0
 */


public class UserToken {

    //主键-用户Token id
    private Integer id;

    //token值
    private String token;

    //用户ID
    private Integer userId;

    //过期时间
    private Date expireTime;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}