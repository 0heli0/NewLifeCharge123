
package com.newlife.charge.interceptor;

import com.newlife.charge.common.IpUtils;
import com.newlife.charge.common.config.Global;
import com.newlife.charge.core.exception.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 充电桩socket层 rpc 服务端 拦截
 * <p>
 */
public class RpcServiceHandlerInterceptor extends HandlerInterceptorAdapter {

    private String ipAddr;

    @Value("${charge.pile.server.ip}")
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        String ipAddr = IpUtils.getIpAddr(request);

        if (ipAddr.matches(this.getIpAddr())||ipAddr.equals("127.0.0.1")) {

        }else {
            throw new BizException("非法IP,访问失败!"+ipAddr+"aa"+ this.getIpAddr());
        }

        return super.preHandle(request, response, handler);
    }
}