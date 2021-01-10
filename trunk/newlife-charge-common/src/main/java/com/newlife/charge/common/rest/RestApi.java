package com.newlife.charge.common.rest;


import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 环信接口调用url
 * <p>
 * Created by linzq on 2017/10/200013 9:27.
 */
@Component
public  class RestApi {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String ORGNAME;

    public static String APPNAME;

    public static String PASSWORD_KEY;

    //获取 APP 管理员 Token 必须值
    public static String CLIENT_ID ;

    //获取 APP 管理员 Token 必须值
    public static String CLIENT_SECRET;

    //获取 APP 管理员 Token 必须值
    public static String GRANT_TYPE;

    //获取token
    public static String TOKEN_URL;

    /*所以接口路径可以参考环信rest api接口http://api-docs.easemob.com/*/
    //调用url 创建用户
    public static String CREATE_USER_URL;

    //获取单个用户,删除单个用户,修改用户昵称路径模板
    public  String getUserUrl;
    //修改单个用户密码路径模板
    public  String modifyUrl;
    //添加好友路径模板 {owner_username} 是要添加好友的用户名，{friend_username} 是被添加的用户名。
    public  String addFriendUrl;

    //创建群路径
    public static String CREATE_GROUP_URL;
    //添加群成员（单个）路径模板
    public String addUserToGroup;
    //添加群成员（多个）路径模板{"usernames":["5cxhactgdj","mh2kbjyop1"]}
    public String addUsersToGroup;

    //发送透传消息
    public static String addMessage;
    //提交数据到身份宝
    public static final String CAMERA_URL = "http://110.86.1.202:9866/identification/api/pcm/faceInfo/add";
    //修改身份宝信息
    public static final String UPDATE_URL = "http://110.86.1.202:9866/identification/api/pcm/faceInfo/update";
    //删除身份宝信息
    public static final String DELETE_URL = "http://110.86.1.202:9866/identification/api/pcm/faceInfo/delete";
    //查找份宝信息
    public static final String FIND_URL = "http://110.86.1.202:9866/identification/api/pcm/faceInfo/find";

    @Value("${easemob.orgname}")
    public static void setORGNAME(String ORGNAME) {
        RestApi.ORGNAME = ORGNAME;
    }

    @Value("${easemob.appname}")
    public static void setAPPNAME(String APPNAME) {
        RestApi.APPNAME = APPNAME;
    }

    @Value("${easemob.password_key}")
    public void setPasswordKey(String passwordKey) {
        PASSWORD_KEY = passwordKey;
    }

    @Value("${easemob.clientid}")
    public void setClientId(String clientId) {
        CLIENT_ID = clientId;
    }

    @Value("${easemob.client_secret}")
    public void setClientSecret(String clientSecret) {
        CLIENT_SECRET = clientSecret;
    }

    @Value("${easemob.grant_type}")
    public void setGrantType(String grantType) {
        GRANT_TYPE = grantType;
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setTokenUrl(String orgnameAndAppName) {
        TOKEN_URL = "https://a1.easemob.com/"+orgnameAndAppName+"/token";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setCreateUserUrl(String orgnameAndAppName) {
        CREATE_USER_URL = "https://a1.easemob.com/"+orgnameAndAppName+"/users";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setGetUserUrl(String orgnameAndAppName) {
        this.getUserUrl = "https://a1.easemob.com/"+orgnameAndAppName+"/users/${username}";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setModifyUrl(String orgnameAndAppName) {
        this.modifyUrl = "https://a1.easemob.com/"+orgnameAndAppName+"/users/${username}/password";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setAddFriendUrl(String orgnameAndAppName) {
        this.addFriendUrl = "https://a1.easemob.com/"+orgnameAndAppName+"/users/${owner_username}/contacts/users/${friend_username}";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setCreateGroupUrl(String orgnameAndAppName) {
        CREATE_GROUP_URL = "https://a1.easemob.com/"+orgnameAndAppName+"/chatgroups";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setAddUserToGroup(String orgnameAndAppName) {
        this.addUserToGroup = "https://a1.easemob.com/"+orgnameAndAppName+"/chatgroups/${group_id}/users/${username}";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setAddUsersToGroup(String orgnameAndAppName) {
        this.addUsersToGroup = "https://a1.easemob.com/"+orgnameAndAppName+"/chatgroups/${group_id}/users";
    }

    @Value("${easemob.orgname}/${easemob.appname}")
    public void setAddMessage(String orgnameAndAppName) {
        RestApi.addMessage = "https://a1.easemob.com/"+orgnameAndAppName+"/messages";
    }
}
