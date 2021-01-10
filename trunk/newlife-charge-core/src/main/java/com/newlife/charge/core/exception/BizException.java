package com.newlife.charge.core.exception;

/**
 * 异常 类
 *
 */
public class BizException extends RuntimeException {

    private int code = ERROR.INTERNAL_ERROR.code();
    private String message;

    public BizException() {

    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(ERROR error) {
        super(error.message());
        this.code = error.code();
        this.message = error.message();
    }

    public BizException(String message) {
        super(message);
        this.message = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
