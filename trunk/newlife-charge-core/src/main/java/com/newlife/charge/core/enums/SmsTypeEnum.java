package com.newlife.charge.core.enums;

/**
 * 发送短信验证码类型
 * <p>
 */
public enum SmsTypeEnum {

    CAR_MOBILE_LOGIN("carMobileLogin", "车主用户短信验证码登录"),
    USER_REGISTER("userRegister", "桩站账号注册"),
    FORGET_PWD("forgetPwd", "忘记密码"),
    MSG_LOGIN("msgLogin", "验证码登入");
//    ACTIVE_USER("activeUser", "激活账户"),
//    MODIFY_BANK("modifyBank", "修改银行卡"),


    private String value;
    private String description;

    SmsTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByValue(String value) {
        for (SmsTypeEnum smsTypeEnum : SmsTypeEnum.values()) {
            if (smsTypeEnum.getValue().equals(value)) {
                return smsTypeEnum.description;
            }
        }
        return null;
    }

    public static String getValueByDescription(String description) {
        for (SmsTypeEnum smsTypeEnum : SmsTypeEnum.values()) {
            if (smsTypeEnum.getDescription().equals(description)) {
                return smsTypeEnum.value;
            }
        }
        return null;
    }
}
