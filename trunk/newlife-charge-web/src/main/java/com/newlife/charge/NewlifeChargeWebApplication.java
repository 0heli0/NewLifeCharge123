package com.newlife.charge;

import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.SpringContextHolder;
import com.newlife.charge.filter.DomainFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
@MapperScan("com.newlife.charge.dao")
@EnableTransactionManagement
@ServletComponentScan  //注册过滤器注解
@EnableCaching
@EnableScheduling //启动定时任务配置
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class NewlifeChargeWebApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(NewlifeChargeWebApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(NewlifeChargeWebApplication.class, args);
    }

    /**
     * 注入 SpringContextHolder
     *
     * @return
     */
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    /**
     * 配置mybatis的分页插件pageHelper
     *
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "false");
        properties.setProperty("dialect", "mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }


    /**
     * 跨域过滤器
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        DomainFilter domainFilter = new DomainFilter();
        registrationBean.setFilter(domainFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }


    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        String multipartCache = System.getProperty("user.dir") + File.separator+"fileUpload"+File.separator+"tmp";
        LOGGER.info("-->文件上传临时路径 multipartCache dir:"+multipartCache);
        File tmpFile = new File(multipartCache);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(multipartCache);
        return factory.createMultipartConfig();
    }

}
