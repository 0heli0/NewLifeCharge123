/**
 * Author: zhengyou
 * Date:   2018/12/14 11:11
 * Descripition:常量
 */
package com.newlife.charge.core.constant;

import java.util.concurrent.TimeUnit;
/**
 * 常量
 */
public class Constants {



    /**数据库整形默认值 0*/
    public static final Integer INTEGER_DEFAULT=0;
    /**空字符串*/
    public static final String STRING_BLANK="";

    /**用户默认密码*/
    public static final String USER_DEFAULT_PASSWORD="123456";


    /**redis中的token前缀*/
    public static final String TOKEN_REDIS_KEY_PREFIX="token_newLifeCharge_";


    /**Token超时时间*/
    public static final long TOKEN_EXPIRE = 2; //2天
    /**Token超时时间单位*/
    public static final TimeUnit TOKEN_EXPIRE_TIME_UNIT = TimeUnit.DAYS;

}
