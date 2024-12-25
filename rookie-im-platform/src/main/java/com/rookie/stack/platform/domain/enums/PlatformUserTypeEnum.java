package com.rookie.stack.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum PlatformUserTypeEnum {

    MAIN_ACCOUNT(1, "主账号"),
    SUB_ACCOUNT(2,"子账户");

    private final Integer status;
    private final String desc;

    private static Map<Integer, PlatformUserTypeEnum> cache;

    static {
        cache = Arrays.stream(PlatformUserTypeEnum.values()).collect(Collectors.toMap(PlatformUserTypeEnum::getStatus, Function.identity()));
    }

    public static PlatformUserTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
