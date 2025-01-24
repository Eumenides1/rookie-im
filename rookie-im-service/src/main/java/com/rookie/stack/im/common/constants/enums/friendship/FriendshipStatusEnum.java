package com.rookie.stack.im.common.constants.enums.friendship;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum FriendshipStatusEnum {
    NORMAL(1, "正常"),       // 正常好友
    DELETED(2, "已删除"),     // 删除的好友关系
    BLOCKED(3, "拉黑"),       // 被拉黑的好友
    DELETED_AND_BLOCKED(4, "删除+拉黑"); // 删除并拉黑的好友

    private final Integer status; // 状态码
    private final String desc;    // 状态描述

    private static final Map<Integer, FriendshipStatusEnum> CACHE;

    static {
        CACHE = Arrays.stream(FriendshipStatusEnum.values())
                .collect(Collectors.toMap(FriendshipStatusEnum::getStatus, Function.identity()));
    }

    /**
     * 根据状态码获取对应的枚举
     *
     * @param type 状态码
     * @return 对应的枚举，若状态码不存在，则返回 null
     */
    public static FriendshipStatusEnum of(Integer type) {
        return CACHE.get(type);
    }
}