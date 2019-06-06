package com.shangguan.upload.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 继承WebMvcConfigurationSupport继承并重写addInterceptor方法用于注册拦截器
 * WebMvcConfigureAdapter已经过时了！！
 */
@Configuration
public class RegisterInterceptor extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeCostInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
