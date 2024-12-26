package com.rookie.stack.platform.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Name：SaTokenConfigure
 * Author：eumenides
 * Created on: 2024/12/26
 * Description:
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {

            // 指定一条 match 规则
            SaRouter
                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/doc.html")
                    .notMatch("/webjars/**")
                    .notMatch("/v3/**")
                    .notMatch("/favicon.ico")
                    .notMatch("/platform_user/**")        // 排除掉的 path 列表，可以写多个
                    .notMatch("/platform_email/**")
                    .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式


        })).addPathPatterns("/**");
    }
}
