package com.newlife.charge.interceptor;

import com.newlife.charge.common.Collections3;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.core.exception.UnauthorizedException;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * api 账号角色权限拦截器
 * <p>
 */
public class ApiRolePermissionsInterceptor extends HandlerInterceptorAdapter {

    private static Logger LOGGER = LoggerFactory.getLogger(ApiRolePermissionsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null) {
            //没有权限
            boolean isPermission = false;

            List<String> permissions = UserContext.getPermissions();

            //方法权限
            String[] permissionsArr = requiresPermissions.value();
            int permissionsLength = permissionsArr.length;
            if (permissionsLength > 0 && Collections3.isNotEmpty(permissions)) {
                for (int i = 0; i < permissionsLength; i++) {
                    if (permissions.contains(permissionsArr[i])) {
                        isPermission = true;
                        break;
                    }
                }
            }
            if (!isPermission) {
                //如果没有权限
                LOGGER.info("期望权限："+ Arrays.toString(permissionsArr));
                LOGGER.info("当前拥有权限列表："+permissions.toString());
                throw new UnauthorizedException(ERROR.UN_PERMISSION);
            }
        }

        return super.preHandle(request, response, handler);
    }
}
