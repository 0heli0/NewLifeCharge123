package com.newlife.charge.service.common;

/**
 */
public class CommonService {

    //20180101160100####
    //中间14位年月日时分秒后4位随机
    protected String getStreamNo() {
        return System.currentTimeMillis() + String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    }
}
