package com.rookie.stack.im.common.constants.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Name：ReadStatusEnum
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@AllArgsConstructor
@Getter
public enum ReadStatusEnum {
    UNREAD(0, "未读"),
    READ(1, "已读");

    private final Integer status; // 状态码
    private final String desc;    // 状态描述

    private static final Map<Integer, ReadStatusEnum> CACHE;

    static {
        CACHE = Arrays.stream(ReadStatusEnum.values())
                .collect(Collectors.toMap(ReadStatusEnum::getStatus, Function.identity()));
    }

    /**
     * 根据状态码获取对应的枚举
     *
     * @param status 状态码
     * @return 对应的枚举，若状态码不存在，则返回 null
     */
    public static ReadStatusEnum of(Integer status) {
        return CACHE.get(status);
    }
}
