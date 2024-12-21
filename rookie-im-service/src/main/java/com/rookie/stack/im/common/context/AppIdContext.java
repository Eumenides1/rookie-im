package com.rookie.stack.im.common.context;

/**
 * Name：AppIdContext
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
public class AppIdContext {
    private static final ThreadLocal<Integer> APP_ID_HOLDER = new ThreadLocal<>();

    // 设置 AppId
    public static void setAppId(Integer appId) {
        APP_ID_HOLDER.set(appId);
    }

    // 获取 AppId
    public static Integer getAppId() {
        return APP_ID_HOLDER.get();
    }

    // 清除 ThreadLocal 中的数据
    public static void clear() {
        APP_ID_HOLDER.remove();
    }
}