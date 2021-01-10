package com.newlife.charge.common.sms;

/**
 * 容联短信返回信息
 *
 * Created by lincz on 2017/11/8 0008.
 */
public class SmsUtilVO {

    private boolean isSendSuccess;

    private String statusCode;

    private String statusMsg;

    public boolean isSendSuccess() {
        return isSendSuccess;
    }

    public void setSendSuccess(boolean sendSuccess) {
        isSendSuccess = sendSuccess;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}
