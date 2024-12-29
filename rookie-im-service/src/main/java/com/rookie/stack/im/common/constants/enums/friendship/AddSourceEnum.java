package com.rookie.stack.im.common.constants.enums.friendship;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AddSourceEnum {
    PHONE(1, "手机号搜索"),
    QR_CODE(2, "扫描二维码"),
    MANUAL_INPUT(3, "手动输入"),
    RECOMMENDATION(4, "名片推荐"),
    OTHER(5, "其他");

    private final Integer source; // 来源标识
    private final String desc;    // 来源描述

    private static final Map<Integer, AddSourceEnum> CACHE;

    static {
        CACHE = Arrays.stream(AddSourceEnum.values())
                .collect(Collectors.toMap(AddSourceEnum::getSource, Function.identity()));
    }

    /**
     * 根据来源标识获取对应的枚举
     *
     * @param source 来源标识
     * @return 对应的枚举，若来源标识不存在，则返回 null
     */
    public static AddSourceEnum of(Integer source) {
        return CACHE.get(source);
    }
}
