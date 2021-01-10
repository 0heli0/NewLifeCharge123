package com.newlife.charge.core.constant;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.newlife.charge.common.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * 登录全局参数
 * <p>
 */
public class UserContext {

    /**
     * 当前用户ID
     */
    private static String USER = "_KEY_USER_ID";
    /**
     * 当前用户类型
     */
    private static final String USER_TYPE = "_KEY_USER_TYPE";

    /**
     * 当前用户状态
     */
    private static final String STATUS = "_KEY_STATUS";

    /**
     * 当前用户名
     */
    private static final String USER_NAME = "_KEY_USER_NAME";

    /**
     * 当前用户真实姓名
     */
    private static final String REAL_NAME = "_KEY_REAL_NAME";

    /**
     * 当前用户昵称
     */
    private static final String NICK_NAME = "_KEY_NICK_NAME";

    /**
     * 当前用户手机号
     */
    private static final String MOBILE = "_KEY_MOBILE";

    /**
     * 当前用户桩站id
     */
    private static final String STATION_ID = "_KEY_STATION_ID";

    /**
     * 当前用户公司id
     */
    private static final String COMPANYINFO_ID = "_KEY_COMPANYINFO_ID";

    /**
     * 当前用户角色id
     */
    private static final String ROLE_ID = "_KEY_ROLE_ID";

    /**
     * 当前用户头像
     */
    private static final String AV_IMAGE = "_KEY_AV_IMAGE";


    /**
     * 用户权限
     */
    private static final String USER_PERMISSIONS = "_KEY_USER_PERMISSIONS";



    private static ThreadLocal<Map<String, String>> CONTEXT = ThreadLocal.withInitial(() -> Maps.newHashMap());

    //当前用户ID
    public static void setUserId(Integer userId) {
        UserContext.CONTEXT.get().put(USER, String.valueOf(userId));
    }

    public static Integer getUserId() {
        return Integer.valueOf(UserContext.CONTEXT.get().get(USER));
    }


    //账号类型
    public static void setUserType(Integer userType) {
        UserContext.CONTEXT.get().put(USER_TYPE, String.valueOf(userType));
    }

    public static Integer getUserType() {
        return Integer.valueOf(UserContext.CONTEXT.get().get(USER_TYPE));
    }


    //用户状态
    public static void setStatus(Integer status) {
        UserContext.CONTEXT.get().put(USER_TYPE, String.valueOf(status));
    }

    public static Integer getStatus() {
        return Integer.valueOf(UserContext.CONTEXT.get().get(STATUS));
    }


    //当前用户名
    public static String getUserName() {
        return UserContext.CONTEXT.get().get(USER_NAME);
    }

    public static void setUserName(String userName) {
        UserContext.CONTEXT.get().put(USER_NAME, userName);
    }

    //当前用户真实姓名
    public static void setRealName(String realName) {
        UserContext.CONTEXT.get().put(REAL_NAME, realName);
    }

    public static String getRealName() {
        return UserContext.CONTEXT.get().get(REAL_NAME);
    }

    //当前用户昵称
    public static void setNickName(String nickName) {
        UserContext.CONTEXT.get().put(NICK_NAME, nickName);
    }

    public static String getNickName() {
        return UserContext.CONTEXT.get().get(NICK_NAME);
    }

    //当前用户手机号
    public static void setMobile(String mobile) {
        UserContext.CONTEXT.get().put(MOBILE, mobile);
    }

    public static String getMobile() {
        return UserContext.CONTEXT.get().get(MOBILE);
    }

    //当前用户桩站id
    public static void setStationId(Integer stationId) {
        UserContext.CONTEXT.get().put(STATION_ID, String.valueOf(stationId));
    }

    public static Integer getStationId() {
        String value = UserContext.CONTEXT.get().get(STATION_ID);
        if (StringUtil.isNotEmpty(value) && !value.equals("null")) {
            return Integer.valueOf(value);
        }
        return null;
    }

    //当前公司id
    public static void setCompanyInfoId(Integer companyInfoId) {
        UserContext.CONTEXT.get().put(COMPANYINFO_ID, String.valueOf(companyInfoId));
    }

    public static Integer getCompanyInfoId() {
        String value = UserContext.CONTEXT.get().get(COMPANYINFO_ID);
        if (StringUtil.isNotEmpty(value) && !value.equals("null")) {
            return Integer.valueOf(value);
        }
        return null;
    }

    //当前用户头像
    public static String getAvatarImg() {
        return UserContext.CONTEXT.get().get(AV_IMAGE);
    }

    public static void setAvatarImg(String avatarImg) {
        UserContext.CONTEXT.get().put(AV_IMAGE, avatarImg);
    }

    //角色id
    public static void setRoleId(Integer roleId) {
        UserContext.CONTEXT.get().put(ROLE_ID, String.valueOf(roleId));
    }

    public static Integer getRoleId() {
        String roleId = UserContext.CONTEXT.get().get(ROLE_ID);
        if(roleId==null){
            return null;
        }
        return Integer.valueOf(roleId);
    }

    public static void setPermissions(List<String> permissions) {
        UserContext.CONTEXT.get().put(USER_PERMISSIONS, JSON.toJSONString(permissions));
    }

    public static List<String> getPermissions() {
        return JSON.parseArray(UserContext.CONTEXT.get().get(USER_PERMISSIONS), String.class);
    }

    //清空
    public static void clear() {
        CONTEXT.get().clear();
    }

}


