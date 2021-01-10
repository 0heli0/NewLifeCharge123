/**
 * Author: zhengyou
 * Date:   2018/12/11 16:15
 * Descripition:Swagger2Config
 */
package com.newlife.charge;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({"dev","test"})
public class Swagger2Config {
    @Bean
    public Docket createRestfulApi() {//api文档实例

        List<Parameter> pars = new ArrayList<>();

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("token令牌")
        .modelRef(new ModelRef("string")).parameterType("header")
        .required(false).build(); //header中的Authorization参数非必填
        pars.add(tokenPar.build());    //根据每个方法名也知道当前方法在设置什么参数


        return new Docket(DocumentationType.SWAGGER_2)//文档类型：DocumentationType.SWAGGER_2
                .apiInfo(apiInfo())//api信息
                .select()//构建api选择器
                .apis(RequestHandlerSelectors.basePackage("com.newlife.charge.controller"))//api选择器选择api的包
                .paths(PathSelectors.any())//api选择器选择包路径下任何api显示在文档中
                .build()//创建文档
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {//接口的相关信息
        return new ApiInfoBuilder()
                .title("新活充电 使用Swagger2构建RESTful接口")
//                .description("接口描述")
                .termsOfServiceUrl("termsOfServiceUrl")
                .contact("zhengyou")
                .version("1.0")
//                .license("http://springfox.github.io/springfox/docs/current/")
//                .licenseUrl("http://springfox.github.io/springfox/docs/current/")
                .build();
    }

}
