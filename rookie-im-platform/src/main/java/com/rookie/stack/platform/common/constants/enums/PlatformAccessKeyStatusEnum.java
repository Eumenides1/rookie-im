package com.rookie.stack.platform.common.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PlatformAccessKeyStatusEnum {
    UNFROZEN(1,"启用"),
    FREEZE(2,"未启用");

    private final Integer status;
    private final String desc;

    private static Map<Integer, PlatformAccessKeyStatusEnum> cache;

    static {
        cache = Arrays.stream(PlatformAccessKeyStatusEnum.values()).collect(Collectors.toMap(PlatformAccessKeyStatusEnum::getStatus, Function.identity()));
    }

    public static PlatformAccessKeyStatusEnum of(Integer type) {
        return cache.get(type);
    }
}
