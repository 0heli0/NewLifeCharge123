package com.newlife.charge.common.sms;

import com.newlife.charge.common.SpringContextHolder;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 环信token生成
 * <p>
 * Created by linzq on 2017/10/23 0013.
 */
public class RedisRestUtils {

    public static final String HX_TOKEN = "hxRestToken";

    public static final String WEI_XIN_TOKEN="accessToken";
    //环信token存储redis
    public static String createRestToken(String application, String token, long expire) {
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        /*stringRedisTemplate.opsForValue().set(HX_TOKEN, token, expire);//3分钟*/
        stringRedisTemplate.opsForValue().set(HX_TOKEN, token, expire,TimeUnit.SECONDS);
        return token;
    }

    //取得环信token
    public static String getRestToken() {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(HX_TOKEN);
    }

    //APP用户token存储redis
    public static String createAppUserToken(String loginName, String token) {
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        stringRedisTemplate.opsForValue().set(loginName, token);
        return token;
    }

    //取得token
    public static String getAppUserToken(String loginName) {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(loginName);
    }

    //删除token
    public static void  deleteAppUserToken(String loginName) {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        redisTemplate.delete(loginName);
        System.out.println(redisTemplate.hasKey(loginName));
    }

    //微信token存储redis
    public static String createWeiXinToken(String token,long expire) {
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        /*stringRedisTemplate.opsForValue().set(HX_TOKEN, token, expire);//3分钟*/
        stringRedisTemplate.opsForValue().set(WEI_XIN_TOKEN, token,expire,TimeUnit.SECONDS);
        return token;
    }

    //取得微信token
    public static String getWeiXinToken() {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(WEI_XIN_TOKEN);
    }
}
