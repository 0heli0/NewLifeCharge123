package com.newlife.charge.controller;


import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.sms.RedisNoTokenSmsUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.exModel.UserEx;
import com.newlife.charge.core.dto.in.CarUserSmsCodeLoginIn;
import com.newlife.charge.core.dto.in.StringIn;
import com.newlife.charge.core.dto.in.UserLoginIn;
import com.newlife.charge.core.dto.in.WeiChatLoginIn;
import com.newlife.charge.core.dto.out.UserLoginOut;
import com.newlife.charge.core.dto.out.WeiChatLoginStatusOut;
import com.newlife.charge.core.enums.SmsTypeEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.security.TokenManager;
import com.newlife.charge.security.TokenPrincipal;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * 登录
 * <p>
 */
@PlatLogModule(moduleId = "/api/login", moduleName = "登录")
@Api(description = "登录,部分不需要token")
@RestController
@RequestMapping("/api/login")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;


    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "退出，需要token", notes = "退出，需要token")
    @PlatLogMethod(operateName = PlatLogMethodType.LOGOUT)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response logout() {
        this.tokenManager.remove(UserContext.getMobile());
        return success();
    }


    /**
     * 总后台手机号密码登录
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN)
    @ApiOperation(value = "登录，不需要token", notes = "登录,不需要token")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<UserLoginOut> login(@RequestBody @Valid @ApiParam(required = true) UserLoginIn userLoginIn,
                                        BindingResult bindingResult) {

        /**抛出错误信息*/
        throwBindingResultErrorMsg(bindingResult);

        UserLoginOut out = null;
        String loginSessionId = userLoginIn.getSessionId();
        String loginCaptchaCode = userLoginIn.getCaptchaCode();
        String userName = userLoginIn.getUserName();
        String password = userLoginIn.getPassword();

        /**登录检查*/
        UserEx userEx = this.userService.loginByMobileCheck(loginSessionId, loginCaptchaCode, userName, password,UserTypeEnum.ADMIN);
//        /**删除旧token*/
//        RedisUtils.deleteRedisValue(Constants.TOKEN_REDIS_KEY_PREFIX + userName);
        /**登录成功*/
        out = this.loginSuccess(userEx);
        return success(out);
    }


    /**
     * 车主用户短信验证码登录
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN)
    @ApiOperation(value = "车主用户短信验证码登录，不需要token", notes = "车主用户短信验证码登录,不需要token")
    @RequestMapping(value = "/car/smsCodeLogin", method = RequestMethod.POST)
    public Response<UserLoginOut> carSmsCodeLogin(@RequestBody @Valid @ApiParam(required = true) CarUserSmsCodeLoginIn in,
                                        BindingResult bindingResult) {

        /**抛出错误信息*/
        throwBindingResultErrorMsg(bindingResult);


        UserLoginOut out = null;
        String mobile = in.getMobile();
        String code = in.getCode();

        /**登录检查*/
        UserEx userEx = this.userService.carUserLoginByMobileCheck(mobile,code,UserTypeEnum.CAR);
//        /**删除旧token*/
//        RedisUtils.deleteRedisValue(Constants.TOKEN_REDIS_KEY_PREFIX + mobile);
        /**登录成功*/
        out = this.loginSuccess(userEx);
        return success(out);

    }

    /**
     *
     * 桩站主子账号web端登录
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN)
    @ApiOperation(value = "桩站登录，不需要token", notes = "桩站登录,不需要token")
    @RequestMapping(value = "/stationLogin", method = RequestMethod.POST)
    public Response<UserLoginOut> stationLogin(@RequestBody @Valid @ApiParam(required = true) UserLoginIn userLoginIn,
                                        BindingResult bindingResult) {

        /**抛出错误信息*/
        throwBindingResultErrorMsg(bindingResult);

        String loginSessionId = userLoginIn.getSessionId();
        String loginCaptchaCode = userLoginIn.getCaptchaCode();
        String userName = userLoginIn.getUserName();
        String password = userLoginIn.getPassword();

        /**登录检查*/
        UserEx userEx = this.userService.loginStationByMobileCheck(loginSessionId, loginCaptchaCode, userName, password,new Integer[]{UserTypeEnum.STATION_MAIN.getValue(), UserTypeEnum.STATION_SUB.getValue()});
        /**登录成功*/
        UserLoginOut out = this.loginSuccess(userEx);
        return success(out);
    }

    /**
     * 桩站小程序 手机验证码端登录
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN)
    @ApiOperation(value = "桩站小程序 手机验证码端登录不需要token", notes = "桩站小程序 手机验证码端登录,不需要token")
    @RequestMapping(value = "/station/smsCodeLogin", method = RequestMethod.POST)
    public Response<UserLoginOut> stationSmsCodeLogin(@RequestBody @Valid @ApiParam(required = true) CarUserSmsCodeLoginIn in,
                                                  BindingResult bindingResult) {

        /**抛出错误信息*/
        throwBindingResultErrorMsg(bindingResult);

        if (!RedisNoTokenSmsUtils.validate(SmsTypeEnum.MSG_LOGIN.getValue(), in.getMobile(), in.getCode())) {
            throw new BizException("验证码错误");
        }

        UserLoginOut out = null;
        String mobile = in.getMobile();
        String code = in.getCode();

        /**登录检查*/
        UserEx userEx = this.userService.loginStationByMobileCodeCheck(mobile,new Integer[]{UserTypeEnum.STATION_MAIN.getValue(), UserTypeEnum.STATION_SUB.getValue()});

        /**登录成功*/
        out = this.loginSuccess(userEx);
        return success(out);

    }


    /**
     * 车主用户通过code获取微信登录信息
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN)
    @ApiOperation(value = "车主用户通过code获取微信登录信息，不需要token", notes = "车主用户通过code获取微信登录信息,不需要token")
    @RequestMapping(value = "/car/carWeiChatGetLoginParam", method = RequestMethod.POST)
    public Response<WeiChatLoginStatusOut> carWeiChatGetLoginParam(@RequestBody @Valid @ApiParam(required = true)StringIn strIn,
                                    BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        WeiChatLoginStatusOut weiChatLoginStatus = this.userService.carWeiChatLogin(strIn);

        return success(weiChatLoginStatus);

    }


    /**
     * 车主用户微信新用户登录或者注册并同时登录
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN)
    @ApiOperation(value = "车主用户微信新用户登录或者注册并同时登录，不需要token", notes = "车主用户微信新用户登录或者注册并同时登录,不需要token")
    @RequestMapping(value = "/car/weiChatRegisterLogin", method = RequestMethod.POST)
    public Response<UserLoginOut> carWeiChatRegisterLogin(@RequestBody @Valid @ApiParam(required = true) WeiChatLoginIn loginIn,
                                            BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        UserEx userEx = this.userService.carWeiChatRegisterLogin(loginIn,UserTypeEnum.CAR);

        UserLoginOut out = this.loginSuccess(userEx);

        return success(out);

    }


    /**
     * 登录成功
     *
     * @param userEx
     * @return
     */
    private UserLoginOut loginSuccess(UserEx userEx) {
        //处理登录时间
        userEx.setLoginTime(DateUtils.getTimestamp());
        updateLoginTime(userEx);

        //用户的权限
        List<String> permissionStrs = this.userService.getUserPermissions(userEx.getId());

        //生成2天效期的token
        String token = this.tokenManager.create(new TokenPrincipal(userEx, permissionStrs));

        //当前手机号拥有的可以切换的账号列表,用户桩站主子账号切换不同的桩站
        int switchToStationCounts=1;
        if(UserTypeEnum.STATION_MAIN.getValue().intValue()==userEx.getUserType()
                || UserTypeEnum.STATION_SUB.getValue().intValue()==userEx.getUserType()){
            List<UserEx> userExes = this.userService.getByMobileAndUserType(userEx.getMobile(),
                    new Integer[]{UserTypeEnum.STATION_MAIN.getValue(), UserTypeEnum.STATION_SUB.getValue()});
            if(userExes!=null){
                switchToStationCounts = userExes.size();
            }
        }


        //输出
        UserLoginOut userLoginOut = new UserLoginOut();
        userLoginOut.setToken(token);//授权的token
        userLoginOut.setUserType(userEx.getUserType());//用户类型
        userLoginOut.setStatus(userEx.getStatus());//账号状态
        userLoginOut.setUserName(userEx.getUserName());//用户名
        userLoginOut.setRealName(userEx.getRealName());//真实姓名
        userLoginOut.setNickName(userEx.getNickName());//昵称
        userLoginOut.setMobile(userEx.getMobile());//手机号
        userLoginOut.setStationId(userEx.getStationId());//桩站ID
        userLoginOut.setCompanyInfoId(userEx.getCompanyInfoId());//公司ID
        userLoginOut.setRoleId(userEx.getRoleId());//角色id

        userLoginOut.setLoginTime(userEx.getLoginTime());//登录时间
        userLoginOut.setLiftBanTime(userEx.getLiftBanTime());//解锁时间
        userLoginOut.setAuditStatus(userEx.getAuditStatus()); //桩站公司审核状态
        userLoginOut.setPermissionStrs(permissionStrs);//用户的权限
        userLoginOut.setSwitchToStationCounts(switchToStationCounts);//当前手机号拥有的可以切换的账号个数(特指桩站端主子账号)
        userLoginOut.setHasMoreStation(userEx.isHasMoreStation());

        return userLoginOut;
    }

    /**
     * 1.更新登录时间
     */
    private void updateLoginTime(UserEx userEx) {
        /**更新登录时间**/
        this.userService.updateLoginTime(userEx);

    }

}
