package com.newlife.charge.security;

/**
 * 登录 的LoginToken
 *
 * Created by lincz on 2018/10/22 0022 16:17.
 */
public class LoginTokenPrincipal {

    private Integer userId;//用户id

    private String mobile;//手机号

    private String realName;//真实姓名

    private String nickName;//呢称

    private String loginName;//登录名

    private String avatarImg;//头像

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }
}
