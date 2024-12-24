package com.rookie.stack.im.common.valid;

import com.rookie.stack.im.common.annotations.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Name：StrongPasswordValidator
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    // 正则表达式：至少一个大写字母、一个小写字母、一个数字、一个特殊字符，长度不少于8位
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d[^A-Za-z\\d]]{8,}$";

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        // 初始化逻辑（如果有需要）
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false; // 空密码无效
        }
        return password.matches(PASSWORD_PATTERN);
    }
}
