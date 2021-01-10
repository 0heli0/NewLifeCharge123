package com.newlife.charge.common.sms;

import com.newlife.charge.common.SpringContextHolder;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * noToken 生成随机手机验证码
 * <p>
 * Created by lincz on 2017/10/13 0013.
 */
public class RedisNoTokenSmsUtils {

    public static final String MOBILE_CODE = "mobileCode";

    //手机验证码存储redis
    public static String createMobileCode(String smsType, String mobile, long expire, TimeUnit timeUnit) {
        String mobileCode = String.valueOf(randomCode());
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        stringRedisTemplate.opsForValue().set(MOBILE_CODE + smsType + mobile, mobileCode, expire, timeUnit);

        return mobileCode;
    }

    //清空redis手机验证码
    public static void clear(String smsType, String mobile) {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        redisTemplate.delete(MOBILE_CODE + smsType + mobile);
    }

    //取得校验码
    public static String getValidateCode(String smsType, String mobile) {
        String key = new StringBuffer(MOBILE_CODE).append(smsType).append(mobile).toString();
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(key);
    }

    //验证校验码
    public static boolean validate(String smsType, String mobile, String validateCode) {
        String code = getValidateCode(smsType, mobile);
        boolean resultBl = validateCode.toUpperCase().equals(code);
        if (resultBl) {
            //清空redis手机验证码
            clear(smsType, mobile);
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
