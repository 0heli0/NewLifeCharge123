package com.newlife.charge.common.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * 发送短信验证码
 * <p>
 * Created by lincz on 2017/10/13 0013 9:27.
 */
@Component
public class SmsUtil {

    public static void main(String[] args) {
        //code 6位数字
        sendCode("13328405283", "123456");
    }

    private static final Logger log = LoggerFactory.getLogger(SmsUtil.class);

    public static final String Template_Code = "msgCode";

    //通知激活模板
    public static final String Template_Activate1 = "243028";

    //通知激活模板
    public static final String Template_Activate2 = "243037";

    //删除家长模板
    public static final String Template_Activate3 = "313124";

    //后台删除家长模板
    public static final String Template_Activate4 = "256285";

    //通知学校负责人升级班级模版
    public static final String Template_Push_School_Admin = "262045";

    private static String smsServerId;

    private static String smsServerPort;

    private static String smsAccountSid;

    private static String smsAccountToken;

    private static String smsAppid;

    public static SmsUtilVO sendCode(String mobile, String code) {
        return sendSms(mobile, Template_Code, new String[]{code, "3分钟"});
    }

    public static SmsUtilVO sendSms(String mobile, String templateId, String[] params) {
        HashMap<String, Object> result = null;

        //初始化SDK
//        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
//        restAPI.init(smsServerId, smsServerPort);

        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
//        restAPI.setAccount(smsAccountSid, smsAccountToken);


        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
//        restAPI.setAppId(smsAppid);


        //******************************注释****************************************************************
        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。																														       *
        //**************************************************************************************************

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
//        result = restAPI.sendTemplateSMS(mobile, templateId, params);

        System.out.println("SDKTestGetSubAccounts result=" + result);

        SmsUtilVO smsUtilVO = new SmsUtilVO();
        smsUtilVO.setStatusCode((String) result.get("statusCode"));
        smsUtilVO.setStatusMsg((String) result.get("statusMsg"));
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
            smsUtilVO.setSendSuccess(true);
        } else {
            //异常返回输出错误码和错误信息
            log.error("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            smsUtilVO.setSendSuccess(false);
        }
        return smsUtilVO;
    }

    public String getSmsServerId() {
        return smsServerId;
    }

    @Value("${sms.server.ip}")
    public void setSmsServerId(String smsServerId) {
        SmsUtil.smsServerId = smsServerId;
    }

    public String getSmsServerPort() {
        return smsServerPort;
    }


    @Value("${sms.server.port}")
    public void setSmsServerPort(String smsServerPort) {
        SmsUtil.smsServerPort = smsServerPort;
    }

    public String getSmsAccountSid() {
        return smsAccountSid;
    }

    @Value("${sms.account.sid}")
    public void setSmsAccountSid(String smsAccountSid) {
        SmsUtil.smsAccountSid = smsAccountSid;
    }

    public String getSmsAccountToken() {
        return smsAccountToken;
    }

    @Value("${sms.account.token}")
    public void setSmsAccountToken(String smsAccountToken) {
        SmsUtil.smsAccountToken = smsAccountToken;
    }

    public String getSmsAppid() {
        return smsAppid;
    }

    @Value("${sms.appid}")
    public void setSmsAppid(String smsAppid) {
        SmsUtil.smsAppid = smsAppid;
    }
}
