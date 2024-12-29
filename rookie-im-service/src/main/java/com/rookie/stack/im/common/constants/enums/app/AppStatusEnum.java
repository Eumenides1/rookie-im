package com.rookie.stack.im.common.constants.enums.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AppStatusEnum {
    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    private final Integer status;
    private final String desc;

    private static Map<Integer, AppStatusEnum> cache;

    static {
        cache = Arrays.stream(AppStatusEnum.values()).collect(Collectors.toMap(AppStatusEnum::getStatus, Function.identity()));
    }

    public static AppStatusEnum of(Integer type) {
        return cache.get(type);
    }
}
