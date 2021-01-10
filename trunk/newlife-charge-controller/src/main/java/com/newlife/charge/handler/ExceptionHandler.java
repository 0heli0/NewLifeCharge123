package com.newlife.charge.handler;

import com.google.common.collect.Lists;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.core.exception.UnauthorizedException;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 全局异常
 */
@ControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Response handlerException(Exception e) {
        /**将异常信息转换成String 再通过 log.error(); 输出到 log4j 的日志文件*/
        logger.error("handlerException：", e);
        return createBody(e);
    }

    private Response createBody(Exception e) {
        Response response = new Response();
        if (e instanceof AuthenticationException) {
            response.error(ERROR.UN_AUTHORIZED);
        } else if (e instanceof BizException) {
            response.setCode(((BizException) e).getCode());
            response.setMessage(((BizException) e).getMessage());
        } else if (e instanceof BindException) {
            pickErrors(((BindException) e).getAllErrors(), response);
        } else if (e instanceof UnauthorizedException) {
            response.setError(ERROR.UN_PERMISSION);
        } else {
            response.error(ERROR.INTERNAL_ERROR);
            response.setMessage("系统错误");
        }
        return response;
    }

    private void pickErrors(List<ObjectError> errors, Response response) {
        List<PickedError> tiny = Lists.newArrayList();
        for (ObjectError error : errors) {
            FieldError fieldError = (FieldError) error;
            tiny.add(new PickedError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue(), fieldError.getCode()));
        }
        response.error(ERROR.INVALID_PARAM).data(tiny);
        if (Collections3.isNotEmpty(tiny)) {
            response.message(tiny.get(0).getMessage());
        }
    }

    @XmlRootElement
    private static class PickedError {
        private String field;
        private String message;
        private Object rejectedValue;
        private String code;

        public PickedError() {
        }

        public PickedError(String field, String message, Object rejectedValue, String code) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
            this.code = code;
        }

        @XmlAttribute
        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        @XmlAttribute
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @XmlAttribute
        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        @XmlAttribute
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
