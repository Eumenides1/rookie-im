package com.rookie.stack.im.common.constants.enums.friendship;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Name：FriendAllowTypeEnum
 * Author：eumenides
 * Created on: 2025/1/18
 * Description:
 */
@AllArgsConstructor
@Getter
public enum FriendAllowTypeEnum {
    /**
     * 无需验证
     */
    NO_VERIFICATION(1, "无需验证"),

    /**
     * 需要验证
     */
    REQUIRE_VERIFICATION(2, "需要验证");

    private final int code;
    private final String description;

    private static final Map<Integer, FriendAllowTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(FriendAllowTypeEnum.values())
                .collect(Collectors.toMap(FriendAllowTypeEnum::getCode, Function.identity()));
    }

    /**
     * 根据状态码获取对应的枚举
     *
     * @param status 状态码
     * @return 对应的枚举，若状态码不存在，则返回 null
     */
    public static FriendAllowTypeEnum of(Integer status) {
        return CACHE.get(status);
    }
}
