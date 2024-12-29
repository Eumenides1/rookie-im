package com.rookie.stack.im.common.annotations.impl;

import com.rookie.stack.common.exception.BusinessException;
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

        // 检查枚举字段是否存在
        boolean fieldExists = Arrays.stream(enumClass.getDeclaredFields())
                .anyMatch(field -> field.getName().equals(enumField));

        if (!fieldExists) {
            // 设置自定义的校验错误信息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("The specified enumField '%s' does not exist in enum class '%s'.",
                                    enumField, enumClass.getName()))
                    .addConstraintViolation();
            return false; // 校验失败
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
                        // 理论上不会进入此分支，因为 initialize 已校验字段存在性
                        throw new BusinessException(
                                String.format("Error accessing field '%s' in enum '%s'.", enumField, enumClass.getName())
                        );
                    }
                });
    }
}
