package com.rookie.stack.im.common.constants.enums.friendship;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum FriendshipRequestStatusEnum {
    PENDING(0, "待处理"),
    APPROVED(1, "同意"),
    REJECTED(2, "拒绝"),
    WITHDRAWN(3, "撤回");

    private final Integer status; // 审批状态码
    private final String desc;    // 状态描述

    private static final Map<Integer, FriendshipRequestStatusEnum> CACHE;

    static {
        CACHE = Arrays.stream(FriendshipRequestStatusEnum.values())
                .collect(Collectors.toMap(FriendshipRequestStatusEnum::getStatus, Function.identity()));
    }

    /**
     * 根据状态码获取对应的枚举
     *
     * @param status 状态码
     * @return 对应的枚举，若状态码不存在，则返回 null
     */
    public static FriendshipRequestStatusEnum of(Integer status) {
        return CACHE.get(status);
    }
}