package com.newlife.charge.interceptor.common.annotation;

import org.springframework.stereotype.Controller;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface PlatLogModule {

    /**
     * 模块id
     * @return
     */
    String moduleId() default "";

    /**
     * 模块名称
     * @return
     */
    String moduleName() default "";
}
