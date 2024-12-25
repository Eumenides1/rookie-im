package com.rookie.stack.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum PlatformUserStatusEnum {
    UNFROZEN(1,"未冻结"),
    FREEZE(2,"已冻结");


    private final Integer status;
    private final String desc;

    private static Map<Integer, PlatformUserStatusEnum> cache;

    static {
        cache = Arrays.stream(PlatformUserStatusEnum.values()).collect(Collectors.toMap(PlatformUserStatusEnum::getStatus, Function.identity()));
    }

    public static PlatformUserStatusEnum of(Integer type) {
        return cache.get(type);
    }
}
