package com.rookie.stack.im.common.interceptor;

import com.rookie.stack.im.common.annotations.SkipAppIdValidation;
import com.rookie.stack.im.common.context.AppIdContext;
import com.rookie.stack.im.common.exception.AppIdMissingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * Name：AppIdInterceptor
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Slf4j
public class AppIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果 handler 是 HandlerMethod，表示是一个具体的控制器方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> beanType = handlerMethod.getBeanType();

            // 如果类上标注了 @SkipAppIdValidation 注解，跳过 AppId 校验
            if (beanType.isAnnotationPresent(SkipAppIdValidation.class)) {
                return true; // 跳过 AppId 校验
            }
            // 如果方法上标注了 @SkipAppIdValidation 注解，跳过 AppId 校验
            if (method.isAnnotationPresent(SkipAppIdValidation.class)) {
                return true; // 跳过 AppId 校验
            }
        }
        // 否则，执行 AppId 校验
        String appId = request.getHeader("AppId");
        if (appId == null || appId.isEmpty()) {
            throw new AppIdMissingException("AppId is missing");
        }
        AppIdContext.setAppId(Integer.valueOf(appId)); // 将 AppId 存入 ThreadLocal
        return true; // 如果携带了 AppId，则继续处理请求
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理 ThreadLocal，避免内存泄漏
        AppIdContext.clear();
    }
}
