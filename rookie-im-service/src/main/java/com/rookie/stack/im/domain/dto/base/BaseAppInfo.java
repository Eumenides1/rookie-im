package com.rookie.stack.im.domain.dto.base;

import com.rookie.stack.im.common.annotations.EnumValueValidator;
import com.rookie.stack.im.common.constants.enums.app.AppTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Name：BaseAppInfo
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Data
public class BaseAppInfo {
    @NotNull
    private String name;
    private String description;
    private String iconUrl;
    private String apiKey;
    @NotNull
    @EnumValueValidator(enumClass = AppTypeEnum.class)
    private Integer type;
    private String callbackUrl;
    private String config;
}
