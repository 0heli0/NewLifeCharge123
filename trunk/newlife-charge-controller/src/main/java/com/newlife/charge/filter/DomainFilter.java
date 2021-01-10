/**
 * Author: zhengyou
 * Date:   2018/12/28 10:03
 * Descripition:跨域
 */
package com.newlife.charge.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一过滤器设置
 */
public class DomainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        //Access-Control-Allow-Credentials  是true的时候。   Access-Control-Allow-Origin"的值不能为*。
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Origin", "http://192.168.101.9:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");//允许跨域的请求方式
        response.setHeader("Access-Control-Max-Age", "0");//预检请求的间隔时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization,Access-Control-Allow-Headers");//允许跨域请求携带的请求头
        response.setHeader("Access-Control-Allow-Credentials","true");//若要返回cookie、携带seesion等信息则将此项设置我true
        response.setHeader("XDomainRequestAllowed","1");


        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}


