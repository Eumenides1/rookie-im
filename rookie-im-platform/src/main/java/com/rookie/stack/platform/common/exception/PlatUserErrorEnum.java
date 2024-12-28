package com.rookie.stack.platform.common.exception;

import com.rookie.stack.common.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Name：PlatUserErrorEnum
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */
@AllArgsConstructor
@Getter
public enum PlatUserErrorEnum implements ErrorEnum {
    PASSWORD_NOT_MATCH(-3001, "密码不合法，请确认后重试！"),
    EMAIL_OR_PASSWORD_ERROR(-3002, "用户名或密码错误，请确认后再试"),
    TOKEN_NOT_EXIST(-3003, "用户未登录，请登录后重试"),
    USER_NO_PERMISSION(-3004, "用户没有权限访问该资源"),
    USER_IS_FROZEN(-3005, "用户已被冻结，请联系管理员解锁"),
    ;
    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
