package com.newlife.charge.common.Reg;

/**
 * Created by lincz on 2017/10/13 0013 10:30.
 */
public interface RegExp {

    /**手机号码格式*/
    String MOBILE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
    /**密码格式：6-18为数字和大小写字母组合*/
    String PASSWORD = "^[A-Za-z0-9]{6,18}$";
    /**枪编号：1或者2*/
    String GUN_CODE = "^[1-2]{1}$";

    /**国际固定电话和手机号码*/
    String CONCAT_PHONE = "^(((\\+\\d{2}-)?0\\d{2,3}-\\d{7,8})|((\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,6,7,8,9][0-9]\\d{8})))$";


}
