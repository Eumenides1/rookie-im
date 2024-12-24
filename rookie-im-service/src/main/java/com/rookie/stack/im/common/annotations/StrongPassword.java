package com.rookie.stack.im.common.annotations;

import com.rookie.stack.im.common.valid.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class) // 指定校验逻辑类
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "密码必须包含大写字母、小写字母、数字和符号，且长度不少于8位";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
