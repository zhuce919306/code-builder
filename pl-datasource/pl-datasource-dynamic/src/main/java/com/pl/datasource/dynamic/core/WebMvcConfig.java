package com.pl.datasource.dynamic.core;


import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClasssName WebMvcConfig
 * @Description
 * @Author liuds
 * @Date 2021/7/8
 * @Version V0.0.1
 */
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ClearTtlDsInterceptor()).addPathPatterns("/**");
    }
}
