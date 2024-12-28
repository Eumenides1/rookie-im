package com.rookie.stack.platform.common.utils;

/**
 * Name：DesensitizationUtil
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
public class DesensitizationUtil {
    /**
     * 通用脱敏方法，用于将敏感信息部分替换为星号
     *
     * @param input  原始字符串
     * @param prefix 保留前缀长度
     * @param suffix 保留后缀长度
     * @return 脱敏后的字符串
     */
    public static String desensitize(String input, int prefix, int suffix) {
        if (input == null || input.length() <= prefix + suffix) {
            // 如果字符串长度不足，返回全星号
            return input == null ? null : "*".repeat(input.length());
        }
        // 保留前缀
        // 中间部分替换为星号
        // 保留后缀
        return input.substring(0, prefix) + // 保留前缀
                "*".repeat(input.length() - prefix - suffix) + // 中间部分替换为星号
                input.substring(input.length() - suffix);
    }
}
