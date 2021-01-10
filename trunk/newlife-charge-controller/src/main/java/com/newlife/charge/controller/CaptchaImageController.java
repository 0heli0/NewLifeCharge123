package com.newlife.charge.controller;


import com.newlife.charge.common.CaptchaImageUtils;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.common.SpringContextHolder;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.in.CaptchaCodeCheckIn;
import com.newlife.charge.core.dto.out.CaptchaOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.Set;

/**
 * 图形验证码
 */
@PlatLogModule(moduleId = "/api/noToken/captcha", moduleName = "图形验证码")
@Api(description = "图形验证码，不需要token")
@RestController
@RequestMapping("/api/noToken/captcha")
public class CaptchaImageController extends BaseController {

    /**
     * 图形验证码,有效期一小时
     *
     * @param request
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "获取图形验证码,有效期一小时")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<CaptchaOut> captcha(HttpServletRequest request) {

        CaptchaOut captchaOut = new CaptchaOut();
        try {
            String sessionId = request.getSession().getId();
            String imgData = CaptchaImageUtils.createImage(sessionId);
            captchaOut.setSessionId(sessionId);
            captchaOut.setImgData(imgData);
            return success(captchaOut);
        } catch (Exception e) {
            LOGGER.error("/api/captcha error ", e);
            return error(captchaOut);
        }

    }

    /**
     * 生成 sessionId
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "生成 sessionId")
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequestMapping(value = "/getSessionId", method = RequestMethod.POST)
    public Response<String> getSessionId(HttpServletRequest request) {

        return success(request.getSession().getId());
    }


    /**
     * 单独验证 图形验证码
     *
     * @return
     */
    @ApiOperation(value = "单独验证 图形验证码,不需要token", notes = "单独验证 图形验证码，不需要token")
    @RequestMapping(value = "/checkCaptcha", method = RequestMethod.POST)
    public Response checkCaptcha(@RequestBody @Valid @ApiParam CaptchaCodeCheckIn captchaCodeCheckIn,
                                 BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        String checkCaptchaCode = captchaCodeCheckIn.getCheckCaptchaCode();
        String sessionId = captchaCodeCheckIn.getSessionId();

        if (StringUtil.isEmpty(checkCaptchaCode)) {
            throw new BizException(ERROR.INVALID_PARAM);
        }

        //验证图形验证码时效性
        String captchaCode = CaptchaImageUtils.getValidateCode(sessionId);
        if (StringUtils.isBlank(captchaCode)) {
            throw new BizException(ERROR.VERIFY_CODE_EXPIRED);
        }
        //验证图形验证码有效性
        if (!StringUtils.equalsIgnoreCase(checkCaptchaCode, captchaCode)) {
            throw new BizException(ERROR.VERIFY_CODE_ERR);
        }

        return success();
    }


    /**
     * 根据sessionId查找图形验证码（测试）
     *
     * @param sessionId
     * @return
     */
    @ApiOperation(value = "根据sessionId查找图形验证码（测试）")
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequestMapping(value = "/getCaptchaCodeBySessionId", method = RequestMethod.POST)
    public Response<String> getCaptchaCodeBySessionId(String sessionId) {

//        String captchaCode = RedisUtils.getRedisValue(CaptchaImageUtils.NAME_SPACE + sessionId);
        String captchaCode = CaptchaImageUtils.getValidateCode(sessionId);
        return success(captchaCode);
    }

    /**
     * 删除所有的图形验证码（测试）
     *
     * @return
     */
    @ApiOperation(value = "删除所有的图形验证码（测试）")
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequestMapping(value = "/removeAllCaptchaCode", method = RequestMethod.GET)
    public Response<String> removeAllCaptchaCode() {

        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);

        Set<String> keys = redisTemplate.keys(CaptchaImageUtils.NAME_SPACE + "*");

        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            RedisUtils.deleteRedisValue(it.next());
        }

        return success().setMessage("删除成功");
    }
}
