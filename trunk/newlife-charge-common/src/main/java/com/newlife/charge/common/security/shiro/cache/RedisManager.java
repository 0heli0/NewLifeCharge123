package com.newlife.charge.common.security.shiro.cache;

import com.newlife.charge.common.config.Global;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisManager {

    private String host = Global.getConfig("jedis.conn.hostName");

    private int port = Integer.parseInt(Global.getConfig("jedis.conn.port"));

    // 0 - never expire
    private int expire = 0;

    //timeout for jedis try to connect to redis server, not expire time! In milliseconds
    private int timeout = 0;

    private String password = Global.getConfig("jedis.conn.password");

    private int database = Integer.parseInt(Global.getConfig("jedis.conn.database"));

    private JedisPoolConfig jedisPoolConfig;

    private static JedisPool jedisPool = null;

    public RedisManager() {
    }

    /**
     * 初始化方法
     */
    public void init() {
        if (jedisPool == null) {
            if (jedisPoolConfig == null) {
                jedisPoolConfig = new JedisPoolConfig();
            }
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
            } else if (timeout != 0) {
                jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
            } else {
                jedisPool = new JedisPool(jedisPoolConfig, host, port);
            }
        }
    }

    /**
     * get value from redis
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        return this.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] execute(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    private <T> T execute(RedisCallback<T> callback) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.select(this.database);
            return callback.execute(jedis);
        } finally {
            jedis.close();
        }
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        return this.set(key, value, this.expire);
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public byte[] set(byte[] key, final byte[] value, final int expire) {
        return this.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] execute(Jedis jedis) {
                jedis.set(key, value);
                if (expire != 0) {
                    jedis.expire(key, expire);
                }
                return value;
            }
        });
    }

    /**
     * del
     *
     * @param key
     */
    public void del(byte[] key) {
        this.execute(new RedisCallback<Void>() {
            @Override
            public Void execute(Jedis jedis) {
                jedis.del(key);
                return null;
            }
        });
    }

    /**
     * flush
     */
    public void flushDB() {
        this.execute(new RedisCallback<Void>() {
            @Override
            public Void execute(Jedis jedis) {
                jedis.flushDB();
                return null;
            }
        });
    }

    /**
     * size
     */
    public Long dbSize() {

        return this.execute(new RedisCallback<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.dbSize();
            }
        });
    }

    /**
     * keys
     *
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;
        Jedis jedis = jedisPool.getResource();
        try {
            keys = jedis.keys(pattern.getBytes());
        } finally {
            jedis.close();
        }
        return keys;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public interface RedisCallback<T> {

        T execute(Jedis jedis);
    }
}
