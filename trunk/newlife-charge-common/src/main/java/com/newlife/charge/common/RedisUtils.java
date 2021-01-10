package com.newlife.charge.common;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 * <p>
 * Created by lincz on 2018/5/29 0029.
 */
public class RedisUtils {

    /**
     * 值存到redis 上
     *
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public synchronized static void setRedisValue(String key, String value, long expire, TimeUnit timeUnit) {
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        if (expire > 0 && timeUnit != null) {
            stringRedisTemplate.opsForValue().set(key, value, expire, timeUnit);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 从redis 上取值
     *
     * @param key
     * @return
     */
    public static String getRedisValue(String key) {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 通过 key 去 redis 上取值，判段是否存在
     *
     * @param key
     * @return
     */
    public static boolean isExistRedis(String key) {
        String redisValue = getRedisValue(key);
        if (StringUtil.isNotEmpty(redisValue)) {
            return true;
        }

        return false;
    }

    /**
     * 通过 key 删除 redis value
     *
     * @param key
     * @return
     */
    public static void deleteRedisValue(String key) {
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        redisTemplate.delete(key);
    }
}
