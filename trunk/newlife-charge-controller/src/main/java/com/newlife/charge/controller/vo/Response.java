package com.newlife.charge.controller.vo;

import com.newlife.charge.core.exception.ERROR;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用输出类
 *
 * @Author lincz on 2018/12/7 0007 13:41.
 */
@ApiModel
public class Response<T> {

    @ApiModelProperty(notes = "状态码，200-成功")
    private int code = 200;
    @ApiModelProperty(notes = "信息")
    private String message;
    @ApiModelProperty(notes = "包含的数据")
    private T data;

    public int getCode() {
        return code;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response<T> message(String message) {
        return setMessage(message);
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Response<T> data(T data) {
        return setData(data);
    }

    public Response<T> setError(ERROR error) {
        this.code = error.code();
        this.message = error.message();
        return this;
    }

    public Response<T> error(ERROR error) {
        this.code = error.code();
        this.message = error.message();
        return this;
    }

}
