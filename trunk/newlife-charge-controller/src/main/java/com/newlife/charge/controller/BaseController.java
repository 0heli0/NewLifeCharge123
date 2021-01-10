package com.newlife.charge.controller;

import com.newlife.charge.common.DateUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;

/**
 * controller 基类
 *
 */
public class BaseController {

    protected static final Logger LOGGER = LoggerFactory.getLogger("");

    @Autowired
    protected Mapper dozer;

    protected <T> Response<T> response(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    protected <T> Response<T> response(int code, String message, T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    protected Response success() {
        return response(null);
    }

    protected <T> Response<T> success(T data) {
        return response(data);
    }

    protected Response error() {
        return response(500, "系统异常!", null);
    }

    protected Response error(ERROR error) {
        return response(error.code(), error.message(), null);
    }
    protected Response error(String message) {
        return response(500, message, null);
    }

    protected <T> Response<T>  error(T data) {
        return response(500, "系统异常!", data);
    }

    protected Response error(int code, String message) {
        return response(code, message, null);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseSqlDate(text));
            }
        });

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseDate(text));
            }
        });

        binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.getTimestamp(text));
            }
        });

        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringUtils.trimToNull(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
    }

    protected void throwBindingResultErrorMsg(BindingResult bindingResult){
        if(bindingResult!=null&&bindingResult.hasErrors()){
            //400 参数错误提示
            throw new BizException(ERROR.INVALID_PARAM.code(),bindingResult.getAllErrors().get(0).getDefaultMessage());
//            throw new BizException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
    }

}
