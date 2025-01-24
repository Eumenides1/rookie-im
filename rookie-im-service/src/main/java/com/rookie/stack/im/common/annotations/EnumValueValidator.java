package com.rookie.stack.im.common.annotations;

import com.rookie.stack.im.common.annotations.impl.EnumValueValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValueValidatorImpl.class) // 指定校验逻辑实现类
@Target({ElementType.FIELD, ElementType.PARAMETER}) // 可以用在字段和方法参数上
@Retention(RetentionPolicy.RUNTIME) // 在运行时保留注解
public @interface EnumValueValidator {

    // 指定要验证的枚举类
    Class<? extends Enum<?>> enumClass();

    // 指定枚举中的字段，用来匹配前端的值（如 "status" 或 "code"）
    String enumField() default "status";

    // 错误信息
    String message() default "参数值不在有效枚举范围内";

    // 下面两个是校验框架需要的属性
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
