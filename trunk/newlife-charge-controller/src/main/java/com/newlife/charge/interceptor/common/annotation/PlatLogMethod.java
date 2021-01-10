package com.newlife.charge.interceptor.common.annotation;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface PlatLogMethod {

    /**
     * 操作key
     *
     * @return
     */
    String operateKey() default "";

    /**
     * 操作名称
     *
     * @return
     */
    String operateName() default "";
}