package com.rookie.stack.im.common.annotations.impl;

import com.rookie.stack.common.exception.BusinessException;
import com.rookie.stack.common.exception.CommonErrorEnum;
import com.rookie.stack.im.common.annotations.EnumValueValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Name：EnumValueValidatorImpl
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
public class EnumValueValidatorImpl implements ConstraintValidator<EnumValueValidator, Object> {

    private Class<? extends Enum<?>> enumClass;
    private String enumField;

    @Override
    public void initialize(EnumValueValidator constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.enumField = constraintAnnotation.enumField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // 参数为空时不进行校验（可根据需求调整）
        }

        // 遍历枚举中的字段进行匹配
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(enumConstant -> {
                    try {
                        Field field = enumConstant.getClass().getDeclaredField(enumField);
                        field.setAccessible(true);
                        Object enumValue = field.get(enumConstant);
                        return value.equals(enumValue);
                    } catch (Exception e) {
                        throw new BusinessException(CommonErrorEnum.PARAM_VALID);
                    }
                });
    }
}
