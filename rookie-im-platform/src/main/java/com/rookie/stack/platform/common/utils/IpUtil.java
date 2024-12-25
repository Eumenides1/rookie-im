package com.rookie.stack.platform.common.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Name：IpUtil
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
public class IpUtil {

    /**
     * 获取用户真实 IP 地址
     *
     * @param request HttpServletRequest
     * @return 用户的 IP 地址
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        // 从常见的请求头中获取 IP 地址
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (isValidIp(ip)) {
                // 如果 X-Forwarded-For 包含多个 IP，用第一个非 unknown 的 IP
                return ip.contains(",") ? ip.split(",")[0].trim() : ip.trim();
            }
        }

        // 如果没有通过代理，直接从请求中获取 IP
        return request.getRemoteAddr();
    }

    /**
     * 验证 IP 地址是否有效
     *
     * @param ip 待验证的 IP
     * @return 是否为有效的 IP
     */
    private static boolean isValidIp(String ip) {
        return ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip);
    }
}
