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
        registry.addInterceptor(new AppIdInterceptor())
                .addPathPatterns("/**")  // 拦截所有路径
                .excludePathPatterns("/v3/**")
                .excludePathPatterns("/swagger-ui/**");  // 排除不需要 AppId 校验的路径
    }
}
