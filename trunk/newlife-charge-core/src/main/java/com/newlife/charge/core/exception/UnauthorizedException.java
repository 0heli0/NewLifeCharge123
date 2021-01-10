package com.newlife.charge.core.exception;

/**
 * 访问权限异常
 *
 */
public class UnauthorizedException extends RuntimeException {

    private int code;
    private String message;

    public UnauthorizedException() {

    }

    public UnauthorizedException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public UnauthorizedException(ERROR error) {
        super(error.message());
        this.code = error.code();
        this.message = error.message();
    }

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
