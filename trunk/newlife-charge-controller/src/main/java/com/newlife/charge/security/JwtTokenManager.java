package com.newlife.charge.security;

import com.alibaba.fastjson.JSON;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.core.constant.Constants;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * token管理器
 * token生成不设置过期时间
 * 使用redis设置过期时间，方便实现token自动延期
 *
 *
 */
@Component
public class JwtTokenManager implements TokenManager {

    private static  final Logger LOGGER = LoggerFactory.getLogger(JwtTokenManager.class);

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * TODO: JwtToken生成需要考虑自动延期问题，
     * 移动端和web端的业务应该不一样（比如移动端应该自动延期，不需要强制登录，web端需要一定时间强制登录一次）
     * 需要写个刷新jwtToken令牌的接口，自动替换要过期的token
     * 目前是讲token存在redis做自动延期
     * @param principal
     * @return
     */
    @Override
    public String create(TokenPrincipal principal) {
        long expire = Constants.TOKEN_EXPIRE;
        TimeUnit timeUnit = Constants.TOKEN_EXPIRE_TIME_UNIT;

        String token = Jwts.builder()
                .setClaims(principal.toMap())
//                JwtToken默认一周有效期，也就是强制要求一周之内必须登录一次。使用redis处理过期时间（2天有效期），方便实现token自动延期
//                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        this.addToRedis(principal,token);

        return  token;
    }

    @Override
    public void remove(String loginName) {
        RedisUtils.deleteRedisValue(Constants.TOKEN_REDIS_KEY_PREFIX+ loginName);
    }

    @Override
    public TokenPrincipal obtains(String token) {
        Jws<Claims> jws;
        try {
            jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (Exception e) {
            //token解析出错
            LOGGER.error("token解析出错:"+token);
            throw new BizException(ERROR.INVALID_TOKEN);
        }
        Claims claims = jws.getBody();

        String mobile = claims.get("mobile", String.class);

        //token过期判断
        String redisToken = RedisUtils.getRedisValue(Constants.TOKEN_REDIS_KEY_PREFIX + mobile);
        if(StringUtils.isBlank(redisToken)){
            throw new BizException(ERROR.INVALID_TOKEN);
        }

        TokenPrincipal principal = new TokenPrincipal();
        principal.setUserId(claims.get("userId", Integer.class));//用户id
        principal.setUserType(claims.get("userType", Integer.class));//用户类型
        principal.setStatus(claims.get("status", Integer.class));//账号状态
        principal.setRealName(claims.get("realName", String.class));//真实姓名
        principal.setUserName(claims.get("userName", String.class));//用户名
        principal.setNickName(claims.get("nickName", String.class));//昵称
        principal.setMobile(claims.get("mobile", String.class));//手机号

        principal.setStationId(claims.get("stationId", Integer.class));//桩站Id
        principal.setRoleId(claims.get("roleId", Integer.class));//角色id
        principal.setLoginTime(claims.get("loginTime", String.class));//登录时间
        principal.setLiftBanTime(claims.get("liftBanTime", String.class));//登录时间
        principal.setAuditStatus(claims.get("auditStatus", Integer.class));//桩站公司审核状态
        principal.setPermissionStrs(JSON.parseArray(claims.get("permissionStrs", String.class), String.class)); //账号所有角色权限


        //token自动延期，每次token使用时，自动重置token过期时间
        this.addToRedis(principal, token);

        return principal;

    }


    @Override
    public void addToRedis(TokenPrincipal principal,String token) {
        //token+mobile+userType
        RedisUtils.setRedisValue(Constants.TOKEN_REDIS_KEY_PREFIX+principal.getMobile(),token,Constants.TOKEN_EXPIRE,Constants.TOKEN_EXPIRE_TIME_UNIT);
    }
}
