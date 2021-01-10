/**
 * Author: Fuq
 * Date:   2018/10/10 16:31
 * Descripition:
 */
package com.newlife.charge.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配制类
 * <p>
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public ApiRolePermissionsInterceptor getApiRolePermissionsInterceptor() {
        return new ApiRolePermissionsInterceptor();
    }

    @Bean
    public RpcServiceHandlerInterceptor getRpcServiceHandlerInterceptor() {
        return new RpcServiceHandlerInterceptor();
    }

    @Bean
    public PlatLogHandlerInterceptor getPlatLogHandlerInterceptor() {
        return new PlatLogHandlerInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截登录的token 并解析
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**/**")
                //放过swagger相关请求
                .excludePathPatterns("/swagger*")
                .excludePathPatterns("/v2*/**")
                .excludePathPatterns("/swagger*/**")
                //放过不需要token的请求
                .excludePathPatterns("/api*/rpc*/**")
//                .excludePathPatterns("/api/rpc/stationClient/doLogin")
                .excludePathPatterns("/api*/noToken*/**")
                //登录
                .excludePathPatterns("/api/login")
                //车主用户短信验证码登录
                .excludePathPatterns("/api/login/car/smsCodeLogin")
                //桩站小程序短信验证码登录
                .excludePathPatterns("/api/login/station/smsCodeLogin")
                //访问上传的文件
                .excludePathPatterns("/upload/**/**")
//                .excludePathPatterns("/error")//TODO:是否需要放过/error???
                //桩站管理用户注册
                .excludePathPatterns("/api*/register*/**")
                //桩站用户手机号登录
                .excludePathPatterns("/api/login/stationLogin")
                .excludePathPatterns("/api/login/car/carWeiChatGetLoginParam")
                .excludePathPatterns("/api/login/car/weiChatRegisterLogin")
                //桩站用户手机号找回密码
                .excludePathPatterns("/api/user/station/resetPass")
                // 微信回调接口
                .excludePathPatterns("/api/appletWeiChatPay/car/weiChatPayCallBack")
                // socket测试
                .excludePathPatterns("/websocket*/**")
                .excludePathPatterns("/checkCenter*/websocket*/**")
        ;


        /**TODO:拦截部分请求 处理请求权限*/
        registry.addInterceptor(getApiRolePermissionsInterceptor())
                //总后台站内信管理-增删改查
                .addPathPatterns("/api/notice/general/pageList")
                .addPathPatterns("/api/notice/general/info")
                .addPathPatterns("/api/notice/general/update")
                .addPathPatterns("/api/notice/general/save")
                .addPathPatterns("/api/notice/general/del")
                .addPathPatterns("/api/notice/general/dels")
                //总后台用户反馈管理
                .addPathPatterns("/api/suggestion/general/pageList")
                //总后台账号管理
                .addPathPatterns("/api/user/general/pageList")
                .addPathPatterns("/api/user/general/save")
                .addPathPatterns("/api/user/general/update")
                .addPathPatterns("/api/user/general/enable")
                .addPathPatterns("/api/user/general/disable")
                .addPathPatterns("/api/user/general/info")
                .addPathPatterns("/api/user/general/del")
                .addPathPatterns("/api/user/general/dels")
                //总后台 用户管理-用户数据查询
                .addPathPatterns("/api/user/general/carUserPageList")
                //总后台 推广建站记录
                .addPathPatterns("/api/generalizeStation/general/pageList")
                .addPathPatterns("/api/generalizeStation/general/delete")
                .addPathPatterns("/api/generalizeStation/general/batchDelete")
                //总后台 角色管理
                .addPathPatterns("/api/role/general/pageList")
                .addPathPatterns("/api/role/general/info")
                .addPathPatterns("/api/role/general/save")
                .addPathPatterns("/api/role/general/update")
                .addPathPatterns("/api/role/general/del")
                //总后台 系统日志管理
                .addPathPatterns("/api/operationLog/general/pageList")
                .addPathPatterns("/api/operationLog/general/detailInfo")
                //总后台 新活资金统计
                .addPathPatterns("/api/newLifeSpendLog/general/pageList")
                .addPathPatterns("/api/newLifeSpendLog/general/info")
                //总后台 充值设置
                .addPathPatterns("/api/preCharge/general/pageList")
                .addPathPatterns("/api/preCharge/general/info")
                .addPathPatterns("/api/preCharge/general/save")
                .addPathPatterns("/api/preCharge/general/update")
                .addPathPatterns("/api/preCharge/general/delete")
                //总后台 桩站账号管理
                .addPathPatterns("/api/companyInfo/general/companyUser/pageList")
                .addPathPatterns("/api/companyInfo/general/companyUser/audit")
                .addPathPatterns("/api/companyInfo/general/companyUser/ration")
                .addPathPatterns("/api/companyInfo/general/companyUser/info")
                //总后台 通用优惠券管理
                .addPathPatterns("/api/coupon/general/pageList")
                .addPathPatterns("/api/coupon/general/save")
                .addPathPatterns("/api/coupon/general/info")
                .addPathPatterns("/api/coupon/general/useList")
                .addPathPatterns("/api/coupon/general/update")
                .addPathPatterns("/api/coupon/general/out")
                .addPathPatterns("/api/coupon/general/delete")
                .addPathPatterns("/api/coupon/general/batchDelete")
                //总后台 站点管理
                .addPathPatterns("/api/stationInfo/general/pageList")
                .addPathPatterns("/api/stationInfo/general/info")
                .addPathPatterns("/api/stationInfo/general/updateInfo")
                .addPathPatterns("/api/stationInfo/general/save")
                .addPathPatterns("/api/stationInfo/general/update")
                //总后台 桩站车位管理
                .addPathPatterns("/api/stationParkingLot/general/pageList")
                .addPathPatterns("/api/stationParkingLot/general/info")
                .addPathPatterns("/api/stationParkingLot/general/save")
                .addPathPatterns("/api/stationParkingLot/general/del")
                .addPathPatterns("/api/stationParkingLot/general/dels")
                //总后台 充电桩管理
                .addPathPatterns("/api/stationClient/general/pageList")
                .addPathPatterns("/api/stationClient/general/info")
                .addPathPatterns("/api/stationClient/general/save")
                .addPathPatterns("/api/stationClient/general/update")
                .addPathPatterns("/api/stationClient/general/del")
                .addPathPatterns("/api/stationClient/general/dels")
                //总后台 充电枪管理
                .addPathPatterns("/api/stationClientGun/general/pageList")
                .addPathPatterns("/api/stationClientGun/general/info")
                .addPathPatterns("/api/stationClientGun/general/save")
                .addPathPatterns("/api/stationClientGun/general/update")
                .addPathPatterns("/api/stationClientGun/general/del")
                .addPathPatterns("/api/stationClientGun/general/dels")
                //总后台 公司信息管理
                .addPathPatterns("/api/companyInfo/general/pageList")
                .addPathPatterns("/api/companyInfo/general/info")
                //总后台 用户资金统计
                .addPathPatterns("/api/userSpendLog/general/pageList")
                .addPathPatterns("/api/userSpendLog/general/info")
                //总后台 桩站资金概览
                .addPathPatterns("/api/stationSpendLog/general/pageList")
                .addPathPatterns("/api/stationSpendLog/general/info")

                //桩站web 费服务费设置
                .addPathPatterns("/api/stationInfo/station/chargeAndServicePriceInfo")
                .addPathPatterns("/api/stationInfo/station/setChargeAndServicePrice")

                //桩站web 时段电价设置
                .addPathPatterns("/api/stationInfo/station/stationTimePriceList")
                .addPathPatterns("/api/stationInfo/station/setStationTimePrice")
                .addPathPatterns("/api/stationInfo/station/deleteStationTimePrice")

                //桩站web 优惠券管理
                .addPathPatterns("/api/coupon/station/couponList")
                .addPathPatterns("/api/coupon/station/save")
                .addPathPatterns("/api/coupon/station/info")
                .addPathPatterns("/api/coupon/station/delete")
                .addPathPatterns("/api/coupon/station/batchDelete")

                //桩站web 角色管理
                .addPathPatterns("/api/role/station/userList")
                .addPathPatterns("/api/role/station/save")
                .addPathPatterns("/api/role/station/update")
                .addPathPatterns("/api/role/station/delete")
                .addPathPatterns("/api/role/station/info")

                //桩站web 子账号管理
                .addPathPatterns("/api/user/station/userList")
                .addPathPatterns("/api/user/station/save")
                .addPathPatterns("/api/user/station/update")
                .addPathPatterns("/api/user/station/dels")
                .addPathPatterns("/api/user/station/info")

                //桩站web 公司信息
                .addPathPatterns("/api/companyInfo/station/companyInfo")

                //桩站web 桩站管理
                .addPathPatterns("/api/stationInfo/station/switchStation")

                //桩站web 用电账单
                .addPathPatterns("/api/chargeLog/station/chargeLogPage")
                .addPathPatterns("/api/chargeLog/station/chargeLog/info")

                //桩站web 资金账单
                .addPathPatterns("/api/newLifeSpendLog/station/pageList")
                .addPathPatterns("/api/newLifeSpendLog/station/info");


        /**拦截所有请求 处理日志*/
        registry.addInterceptor(getPlatLogHandlerInterceptor())
                .addPathPatterns("/**/**")
                .excludePathPatterns("/swagger*")
                .excludePathPatterns("/v2*/**")
                .excludePathPatterns("/swagger*/**")
                .excludePathPatterns("/upload/**/**")
                .excludePathPatterns("/api*/minioRest*/**");

        /**拦截/api/rpc请求，处理rpc服务端*/
        registry.addInterceptor(getRpcServiceHandlerInterceptor())
                .addPathPatterns("/api/rpc/**");


    }
}
