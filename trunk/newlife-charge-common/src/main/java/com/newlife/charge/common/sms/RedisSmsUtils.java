package com.newlife.charge.common.sms;

import com.newlife.charge.common.SpringContextHolder;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 生成随机手机验证码
 * <p>
 * Created by lincz on 2017/10/13 0013.
 */
public class RedisSmsUtils {

    public static final String MOBILE_CODE = "mobileCode";

    //手机验证码存储redis
    public static String createMobileCode(String smsType, String mobile, int userId, long expire, TimeUnit timeUnit) {
        String mobileCode = String.valueOf(randomCode());
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        stringRedisTemplate.opsForValue().set(MOBILE_CODE + smsType + mobile + userId, mobileCode, expire, timeUnit);//3分钟

        return mobileCode;
    }

    //清空redis手机验证码
    public static void clear(String smsType, String mobile, int userId) {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        redisTemplate.delete(MOBILE_CODE + smsType + mobile + userId);
    }

    //取得校验码
    public static String getValidateCode(String smsType, String mobile, int userId) {
        String key = new StringBuffer(MOBILE_CODE).append(smsType).append(mobile).append(userId).toString();
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(key);
    }

    //验证校验码
    public static boolean validate(String smsType, String mobile, int userId, String validateCode) {
        String code = getValidateCode(smsType, mobile, userId);

        boolean resultBl = validateCode.toUpperCase().equals(code);
        if (resultBl) {
            //清空redis手机验证码
            //clear(smsType, mobile, userId);
        }

        return resultBl;
    }

    //随机数字
    public static int randomCode() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    public static void main(String[] args) {
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println(i);
    }
}
