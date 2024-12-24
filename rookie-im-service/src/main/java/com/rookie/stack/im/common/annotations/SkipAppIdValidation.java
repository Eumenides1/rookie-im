package com.rookie.stack.im.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 该注解用于标注不需要 AppId 校验的方法
@Target({ElementType.METHOD, ElementType.TYPE}) // 支持类和方法
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipAppIdValidation {
}