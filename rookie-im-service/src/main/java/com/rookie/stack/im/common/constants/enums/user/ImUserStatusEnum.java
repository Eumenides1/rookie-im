package com.rookie.stack.im.common.constants.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Name：ImUserTagEnum
 * Author：eumenides
 * Created on: 2024/12/19
 * Description:
 */
@AllArgsConstructor
@Getter
public enum ImUserStatusEnum {
    ENABLED(0, "启用"),
    DISABLED(1, "禁用"),
    DELETED(2, "已删除"),
    NOT_DELETED(3, "未删除");

    private final Integer status;
    private final String desc;

    private static Map<Integer, ImUserStatusEnum> cache;

    static {
        cache = Arrays.stream(ImUserStatusEnum.values()).collect(Collectors.toMap(ImUserStatusEnum::getStatus, Function.identity()));
    }

    public static ImUserStatusEnum of(Integer type) {
        return cache.get(type);
    }
}
