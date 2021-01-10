package com.newlife.charge.security;

import java.util.concurrent.TimeUnit;

/**
 * token 生成 与解析
 *
 * @Author lincz on 2018/12/10 0010 11:26.
 */
public interface TokenManager {

    /**
     * 创建token
     *
     * @return
     */
    String create(TokenPrincipal principal);

    /**
     * 删除token
     * @param loginName
     */
    void remove(String loginName);

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    TokenPrincipal obtains(String token);


    /**
     * token加入Redis,或者刷新Token有效期
     */
    void addToRedis(TokenPrincipal principal,String token);



}
