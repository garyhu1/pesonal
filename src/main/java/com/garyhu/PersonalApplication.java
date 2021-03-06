package com.garyhu;

import com.garyhu.filter.HttpBearerAuthorizeAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: garyhu
 * @since: 2018/10/26 0026
 * @decription: 1、打包成war包时需要集成SpringBootServletInitializer，实现configure方法
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching  // 开启缓存
public class PersonalApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(PersonalApplication.class);
    }

    public static void main(String[] args)throws Exception{
        SpringApplication.run(PersonalApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean jwtFilterRegisterBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HttpBearerAuthorizeAttribute httpBearerFilter = new HttpBearerAuthorizeAttribute();
        registrationBean.setFilter(httpBearerFilter);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/user/getUsers");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
