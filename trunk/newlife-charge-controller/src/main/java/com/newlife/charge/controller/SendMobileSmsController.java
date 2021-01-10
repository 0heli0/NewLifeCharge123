package com.newlife.charge.controller;

import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.common.SpringContextHolder;
import com.newlife.charge.common.ms.vo.SmsModel;
import com.newlife.charge.common.sms.RedisNoTokenSmsUtils;
import com.newlife.charge.common.sms.SmsUtil;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.in.SmsNoToken;
import com.newlife.charge.core.enums.SmsTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.SmsUtilService;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 发送短信验证码
 */
@PlatLogModule(moduleId = "/api/noToken/sms", moduleName = "短信模块")
@Api(description = "发送短信验证码,不需要token")
@RestController
@RequestMapping("/api/noToken/sms")
public class SendMobileSmsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SendMobileSmsController.class);

    @Value("${sms.isSend}")
    private boolean smsIsSend;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsUtilService smsUtilService;


    /**
     * 根据手机号和短信类型查找短信验证码（测试）
     *
     * @param smsNoToken
     * @return
     */
    @ApiOperation(value = "根据手机号和短信类型查找短信验证码（测试）")
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequestMapping(value = "/getByMobileAndType", method = RequestMethod.POST)
    public Response<String> getByMobileAndType(@RequestBody @ApiParam SmsNoToken smsNoToken) {
        String captchaCode = RedisUtils.getRedisValue(RedisNoTokenSmsUtils.MOBILE_CODE + smsNoToken.getSmsType() + smsNoToken.getMobile());
        return success(captchaCode);
    }

    /**
     * 删除指定手机号指定类型的短信验证码（测试）
     *
     * @return
     */
    @ApiOperation(value = "删除指定手机号指定类型的短信验证码（测试）")
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequestMapping(value = "/removeMobileCode", method = RequestMethod.POST)
    public Response<String> removeMobileCode(@RequestBody @ApiParam SmsNoToken smsNoToken) {

        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);

        Set<String> keys = redisTemplate.keys(RedisNoTokenSmsUtils.MOBILE_CODE + smsNoToken.getSmsType() + smsNoToken.getMobile());

        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            RedisUtils.deleteRedisValue(it.next());
        }
        return success().setMessage("删除成功");
    }


    /**
     * 删除所有手机号所有的短信验证码（测试）
     *
     * @return
     */
    @ApiOperation(value = "删除所有手机号所有的短信验证码（测试）")
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequestMapping(value = "/removeMobileCodeAll", method = RequestMethod.POST)
    public Response<String> removeMobileCodeAll() {

        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);

        Set<String> keys = redisTemplate.keys(RedisNoTokenSmsUtils.MOBILE_CODE + "*");

        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            RedisUtils.deleteRedisValue(it.next());
        }
        return success().setMessage("删除成功");
    }

    /**
     * 发送短信验证码 不需要token
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "发送短信验证码", notes = "发送不同类型的短信验证码，发送类型,如下：" +
            "carMobileLogin-车主用户短信验证码登录,userRegister-桩站账号注册,forgetPwd-忘记密码,msgLogin-验证码登入")
    @RequestMapping(value = "/sendNoTokenSms", method = RequestMethod.POST)
    public Response sendNoTokenSms(@Valid @RequestBody @ApiParam SmsNoToken smsNoToken, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);
        SmsModel smsModel = null;

        long expire = 30;//默认过期时间，30分钟
        String smsType = smsNoToken.getSmsType();

        /**短信验证码*/
        String mobileCode;

        //根据配置判断是否需要发送短信
        if (smsIsSend) {
            smsModel = this.smsUtilService.validBeforeSend(smsNoToken.getMobile(), SmsUtil.Template_Code);
            if (smsModel != null) {
                return error(smsModel.getCode(), smsModel.getMessage());
            }

            /**创建短信验证码*/
            mobileCode = RedisNoTokenSmsUtils.createMobileCode(smsNoToken.getSmsType(), smsNoToken.getMobile(), expire, TimeUnit.MINUTES);

            if (SmsTypeEnum.CAR_MOBILE_LOGIN.getValue().equals(smsType)) {
                //carMobileLogin-车主用户短信验证码登录,一天只能获取三次，提示：“今天已获取三次，请明天再获取！”
                String key = SmsTypeEnum.CAR_MOBILE_LOGIN.getValue() + "_" + smsNoToken.getMobile();
                String value = RedisUtils.getRedisValue(key);
                int count = 0;//已经发送次数
                if (StringUtils.isNotBlank(value)) {
                    count = Integer.parseInt(value);
                }else{
                    count=0;
                }
                if(count>=3){
                    return error("今天已获取三次，请明天再获取！");
                }

                //获取到今晚24点还剩多少秒，此过期时间是为了到凌晨是能自动清除，实现每天3次
                Date nowDate = new Date();
                Timestamp minOffsetDay = DateUtils.getMinOffsetDay(nowDate, 1);
                long time = (minOffsetDay.getTime()-nowDate.getTime())/1000;//到今晚24点还剩多少秒

                count++;
                RedisUtils.setRedisValue(SmsTypeEnum.CAR_MOBILE_LOGIN.getValue() + "_" + smsNoToken.getMobile(),
                        String.valueOf(count),time,TimeUnit.SECONDS);

            }

            //发送短信
            smsModel = this.smsUtilService.sendSms(smsNoToken.getMobile(), SmsUtil.Template_Code, new String[]{mobileCode, "" + expire + "分钟"});
            if (smsModel.getCode() == 200) {
                logger.info("手机：" + smsNoToken.getMobile() + "验证码：" + mobileCode + "," + smsModel.getMessage());
                return success().setMessage(smsModel.getMessage());
            } else {

                logger.info("手机：" + smsNoToken.getMobile() + "验证码：" + mobileCode + "，发送失败：" + smsModel.getCode() + "-" + smsModel.getMessage());
                return error(smsModel.getMessage());
            }

        } else {
            //不发短信，就返回验证码
            /**创建短信验证码*/
            mobileCode = RedisNoTokenSmsUtils.createMobileCode(smsNoToken.getSmsType(), smsNoToken.getMobile(), expire, TimeUnit.MINUTES);
            return success(mobileCode).setMessage("发送成功");
        }
    }
}
