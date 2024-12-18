package com.rookie.stack.im.config;

import com.rookie.stack.im.common.interceptor.AppIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Name：WebConfig
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，拦截所有路径的请求
        registry.addInterceptor(new AppIdInterceptor()).addPathPatterns("/**");
    }
}
