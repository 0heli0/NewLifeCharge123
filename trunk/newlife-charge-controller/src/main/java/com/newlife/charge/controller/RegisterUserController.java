package com.newlife.charge.controller;


import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.sms.RedisNoTokenSmsUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.UserEx;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.enums.SmsTypeEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号注册/桩站后台
 */
@PlatLogModule(moduleId = "/api/register", moduleName = "账号注册/桩站后台")
@Api(description = "账号注册/桩站后台")
@RestController
@RequestMapping("/api/register")
public class RegisterUserController extends BaseController {


    @Autowired
    private UserService userService;

    /**
     * 验证 手机号码是否注册
     *
     * @param mobile
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "验证 手机号码是否注册", notes = "验证 手机号码是否注册")
    @RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
    public Response checkMobile(String mobile) {
        //1.检查手机号是否已经注册
        Map<String, Object> map = new HashMap();
        boolean isRegister = false;
        String message = "手机未被注册";
        List<UserEx> stationUser = userService.getByMobileAndUserType(mobile, new Integer[]{UserTypeEnum.STATION_MAIN.getValue(), UserTypeEnum.STATION_SUB.getValue()});
        if (Collections3.isNotEmpty(stationUser)) {
            isRegister = true;
            message = "手机号已被注册";
        }
        map.put("isRegister",isRegister);
        map.put("message",message);
        return success(map);
    }

    /**
     * 短信验证码 验证
     *
     * @param userRegisterIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "短信验证码 验证", notes = "短信验证码 验证")
    @RequestMapping(value = "/checkMsgCode", method = RequestMethod.POST)
    public Response checkMsgCode(@RequestBody @Valid @ApiParam(required = true) UserRegisterIn userRegisterIn) {
        if (!RedisNoTokenSmsUtils.validate(userRegisterIn.getSmsType(), userRegisterIn.getMobile(), userRegisterIn.getMsgCode())) {
            throw new BizException("验证码错误");
        }

        return success().setMessage("验证成功");
    }

    /**
     * 桩站管理后台 账号注册
     *
     * @param userRegisterPasswordIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "桩站管理后台 账号注册", notes = "桩站管理后台 账号注册")
    @RequestMapping(value = "/station/registerUser", method = RequestMethod.POST)
    public Response registerUser(@RequestBody @Valid @ApiParam(required = true) UserRegisterPasswordIn userRegisterPasswordIn) {

        //注册
        this.userService.register(userRegisterPasswordIn);

        return success().setMessage("注册成功");
    }

}
