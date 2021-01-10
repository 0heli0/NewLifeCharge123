package com.newlife.charge.core.exception;

/**
 * 异常信息 枚举
 */
public enum ERROR {


    INVALID_TOKEN(600001, "无效token"),
    UN_PERMISSION(600002, "没有操作权限"),
    VERIFY_CODE_ERR(600003, "请输入正确的验证码"),
    VERIFY_CODE_EXPIRED(600004, "图形验证码已失效，请重新获取"),
    UN_AUTHORIZED(600005, "用户名或密码错误"),

    EXISTS_DATA_ALREADY(600431, "数据已存在"),
    EXISTS_STATION_CODE(600451, "编号已存在"),
    EXISTS_STATION_TERMINAL_NO(600452, "充电桩终端号已存在"),
    INVALID_MOBILE(600402,"手机号不正确"),
    DATA_NOT_FOUND(600425,"没有数据"),
    NOT_FOUND_USER(600427, "用户不存在，请先注册"),
    USER_STATUS_DISABLE(600428, "用户已被禁用"),
    USER_STATUS_LOCKED(600429, "用户已被锁定"),
    MOBILE_FOUND_USER(600451, "手机号已被注册"),
    MOBILE_FOUND_USER_SUB(600452,"手机号已被添加成子账号"),
    PARAMATER_ERROR(600006,"参数不符合要求,请检查传入参数"),
    RESULT_NULL(600007,"请求未返回结果，请检查参数是否正确"),
    MOBILE_REPEAT_ADMIN(600008, "该手机号不可重复添加为总后台账号"),
    MOBILE_UPDATE_ADMIN(600009, "该手机号已添加为总后台账号,不能更新为该手机号"),
    SMS_CODE_EXPIRED(600010, "短信验证码已失效，请重新获取"),
    SMS_CODE_ERR(600011, "短信验证码错误，请重新输入"),
    COUPON_NONE(600012, "券已被领完，下次早点来哦"),
    COUPON_FINISH(600013, "优惠券领取时间已过，下次早点来吧"),
    COUPON_LIMIT(600014, "您已领取该券，别太贪心哦"),
    COUPON_UN_USE(600015, "您已领取过该券，请先使用，再来领哦"),

    NOT_DELETE_ONESELF(600101, "不能删除自己的账号"),
    ROLE_HAS_USED_NOT_DELETE(600102, "角色已被使用不能删除"),
    ROLE_NAME_EXIST(600103, "角色名已存在"),

    USER_BALANCE_ERROR(600201, "用户金额核算错误，请联系工作人员审核用户金额后再进行操作"),
    STATION_CLIENT_GUN_CONNECT_FAIL(600202, "充电枪连接失败"),
    PRE_CHARGE_REPEAT(600203, "预充值面额重复"),
    COUPON_REPEAT(600203, "请勿添加重复的优惠券"),
    OPEN_ORDER(600204, "当前还有未结束订单"),
    NOT_WEI_XIN_USER(600205, "当前账号没有绑定微信号，请选择使用微信快速登录绑定手机号再退款"),
    USER_DIFFERENT(600206, "当前账号绑定的微信号和当前微信号不匹配"),


    STATION_INFO_NOT_EXIST(600301, "桩站信息不存在"),
    STATION_INFO_DELETED(600302, "桩站已删除"),
    STATION_CLIENT_INFO_NOT_EXIST(600303, "充电桩信息不存在"),
    STATION_PARKING_LOT_NOT_EXIST(600304, "车位信息不存在"),
    STATION_PARKING_LOT_USED(600305, "该车位已被其他的充电枪绑定!"),
    COMPANY_INFO_NOT_EXIST(600306, "公司信息不存在"),
    COMPANY_AUDIT_STATUS_NOT_PASS(600307, "公司资质认证审核未通过"),
    STATION_CLIENT_HAS_MAX_GUN(600308, "充电桩已到达绑定枪数的上限，请选择其他充电桩"),
    STATION_PARKING_LOT_HAS_GUN(600309, "已绑定充电枪的车位不能删除"),
    STATION_CLIENT_HAS_GUN(600310, "已绑定充电枪的充电桩不能删除"),
    STATION_CLIENT_GUN_INFO_NOT_EXIST(600311, "充电枪信息不存在"),


    INVALID_PATH(403, "访问地址非法"),
    NOT_FOUND(404, "{0}未找到"),
    INVALID_PARAM(400, "参数错误"),
    INTERNAL_ERROR(500, "服务器繁忙，请稍后再试");

    private final int code;
    private final String message;

    ERROR(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
