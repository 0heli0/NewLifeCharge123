package com.newlife.charge.interceptor;


import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.security.TokenManager;
import com.newlife.charge.security.TokenPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录 loginToken 拦截器
 * TODO:涉及到多端登录，可能需要在登录参数中加入用户类型
 * <p>
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        String requestURI = request.getRequestURI();
        logger.error("请求url:" + requestURI);
        final String authHeader = request.getHeader("Authorization");
        if (!StringUtils.startsWith(authHeader, "Bearer ")) {
            /**TIPS：*/
//            excludePathPatterns方法是排除访问路径,但是当你排除的url路径在项目中并不存在的时候,springboot会将路径编程/error,从而无法进行排除.
            logger.error("无效token:" + authHeader);
            throw new BizException(ERROR.INVALID_TOKEN);
        }
        final String loginToken = StringUtils.substring(authHeader, 7);
        if(StringUtils.isBlank(loginToken)){
            logger.error("token未传");
            throw new BizException(ERROR.INVALID_TOKEN);
        }


        //解析loginToken
        TokenPrincipal principal = this.tokenManager.obtains(loginToken);
        if (principal != null) {
            UserContext.setUserId(principal.getUserId());//用户id
            UserContext.setUserType(principal.getUserType());//当前用户类型
            UserContext.setStatus(principal.getStatus());//当前用户状态
            UserContext.setUserName(principal.getUserName());//用户名
            UserContext.setNickName(principal.getNickName());//昵称
            UserContext.setRealName(principal.getRealName());//真实姓名
            UserContext.setMobile(principal.getMobile());//手机号
            UserContext.setStationId(principal.getStationId());//当前用户桩站id
            UserContext.setCompanyInfoId(principal.getCompanyInfoId());//当前用户公司id
            UserContext.setRoleId(principal.getRoleId());//当前用户角色id
            UserContext.setPermissions(principal.getPermissionStrs());//账号所有角色权限
        }
        return super.preHandle(request, response, handler);
    }
}
