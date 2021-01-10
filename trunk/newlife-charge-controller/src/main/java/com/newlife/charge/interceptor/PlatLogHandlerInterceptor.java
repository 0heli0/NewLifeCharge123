package com.newlife.charge.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.IpUtils;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.config.RequestWrapper;
import com.newlife.charge.core.constant.Constants;
import com.newlife.charge.core.domain.OperationLog;
import com.newlife.charge.core.enums.log.PlatLogOperateType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.security.TokenManager;
import com.newlife.charge.security.TokenPrincipal;
import com.newlife.charge.service.OperationLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志
 */
public class PlatLogHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatLogHandlerInterceptor.class);

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private OperationLogService operationLogService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PlatLogMethod platLogMethod = handlerMethod.getMethodAnnotation(PlatLogMethod.class);
            if (platLogMethod != null) {
                PlatLogModule platLogModule = AnnotationUtils.getAnnotation(handlerMethod.getMethod().getDeclaringClass(), PlatLogModule.class);
                if (platLogModule != null) {
                    OperationLog platLog = this.createPlatLog(request, platLogModule, platLogMethod);

                    LOGGER.warn(new StringBuilder("loginName:{},operationType:{},operationText:{},action:{},operationMoudle:{},")
                                    .append("requestUrl:{},ip:{},createTime:{}").toString(),
                            platLog.getLoginName(), platLog.getOperationType(), platLog.getOperationText(), platLog.getAction(), platLog.getOperationMoudle(),
                            platLog.getRequestUrl(), platLog.getIp(), platLog.getCreateTime());

                    if (platLog.getUserId() != 0) {
                        //保存日志
                        this.operationLogService.save(platLog);
                    }
                }
            }
        }
    }

    private OperationLog createPlatLog(HttpServletRequest request, PlatLogModule platLogModule, PlatLogMethod platLogMethod) {


        Integer userId = 0;
        Integer userType = 0;
        String userName = "";
        Integer stationId = null;
        Integer operationType = null;
        String operationText = null;

        String params_str = null;
        JSONObject params = null;


        //请求参数
        try {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            params_str = requestWrapper.getBody();
            if (StringUtils.isNotBlank(params_str)) {
                params = JSON.parseObject(params_str);
                operationText = JSON.toJSONString(params);
            }
        } catch (Exception e) {

            LOGGER.error("request转换出错:params=" + params_str + ",operationText=" + params, e);
            throw new BizException(ERROR.INVALID_PARAM);
        }

        //判断操作类型：登录还是业务
        String requestUrl = request.getRequestURI();
        if (requestUrl.contains("login")) {
            //登录、退出、切换账号登录
            if (requestUrl.contains("logout")) {
                //退出
                operationType = PlatLogOperateType.Login.getValue();
                operationText = "退出成功";
            } else if (requestUrl.contains("switch")) {
                //切换账号
                operationType = PlatLogOperateType.Login.getValue();
                operationText = "登录成功";
            } else {
                //登录
                operationType = PlatLogOperateType.Login.getValue();
                String loginName = params.getString("loginName");//其实就是手机号mobile
                if(StringUtils.isBlank(loginName)){
                    loginName = params.getString("userName");//其实就是手机号mobile
                }

                String loginToken = RedisUtils.getRedisValue(Constants.TOKEN_REDIS_KEY_PREFIX + loginName);
                if (StringUtils.isNotBlank(loginToken)) {
                    operationText = "登录成功";
                    //解析loginToken
                    try {
                        TokenPrincipal principal = this.tokenManager.obtains(loginToken);
                        if (principal != null) {
                            userId = principal.getUserId();
                            userType = principal.getUserType();
                            userName = principal.getUserName();
                            stationId = principal.getStationId();
                        } else {
                            userName = "登录失败";
                        }
                    } catch (Exception e) {
                        userName = "token过期或解析失败";
                    }

                } else {
                    operationText = "登录失败";
                }
            }


        } else {
            //业务
            operationType = PlatLogOperateType.Common.getValue();

            final String authHeader = request.getHeader("Authorization");
            if (StringUtils.isBlank(authHeader)) {
                userName = "token为空";
            } else if (!authHeader.startsWith("Bearer ")) {
                userName = "token格式错误";
            } else {
                final String loginToken = StringUtils.substring(authHeader, 7);
                //解析loginToken
                try {
                    TokenPrincipal principal = this.tokenManager.obtains(loginToken);
                    if (principal != null) {
                        userId = principal.getUserId();
                        userName = principal.getUserName();
                        stationId = principal.getStationId();
                    } else {
                        userName = "登录失败";
                    }
                } catch (Exception e) {
                    userName = "token过期或解析失败";
                }
            }
        }

        //返回
        OperationLog operationLog = new OperationLog();
        operationLog.setUserId(userId);
        operationLog.setUserType(userType);
        operationLog.setStationId(stationId==null?0:stationId);
        operationLog.setLoginName(userName);
        operationLog.setOperationType(operationType);
        if (StringUtils.isNotBlank(platLogMethod.operateName())) {
            operationLog.setAction(platLogMethod.operateName());
        } else {
            operationLog.setAction("");
        }
        operationLog.setOperationMoudle(platLogModule.moduleName());
        operationLog.setRequestUrl(requestUrl);
        operationLog.setIp(IpUtils.getIpAddr(request));
        operationLog.setCreateTime(DateUtils.getTimestamp());
        operationLog.setOperationText(operationText);

        return operationLog;
    }
}
