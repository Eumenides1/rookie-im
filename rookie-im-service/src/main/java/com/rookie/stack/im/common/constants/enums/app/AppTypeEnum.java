package com.rookie.stack.im.common.constants.enums.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AppTypeEnum {

    CHAT(1,"chat", "聊天应用"),
    NOTIFICATION(2,"notification", "通知应用"),
    FILE_TRANSFER(3,"file_transfer", "文件传输应用"),
    VIDEO_CALL(4,"video_call", "视频通话应用"),
    CUSTOM(5,"custom", "自定义应用");

    private final Integer status;
    private final String code;
    private final String desc;

    private static Map<Integer, AppTypeEnum> cache;

    static {
        cache = Arrays.stream(AppTypeEnum.values()).collect(Collectors.toMap(AppTypeEnum::getStatus, Function.identity()));
    }

    public static AppTypeEnum of(Integer type) {
        return cache.get(type);
    }

    public static boolean isValidStatus(Integer status) {
        return cache.containsKey(status);
    }

    public static boolean isValidCode(String code) {
        return Arrays.stream(AppTypeEnum.values())
                .anyMatch(enumValue -> enumValue.getCode().equalsIgnoreCase(code));
    }

    public static boolean isValid(Integer status, String code) {
        if (status != null) {
            return isValidStatus(status);
        }
        if (code != null) {
            return isValidCode(code);
        }
        return false;
    }
}
