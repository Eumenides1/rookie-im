package com.rookie.stack.im.common.exception.platform;

import com.rookie.stack.im.common.exception.ErrorEnum;
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
    EMAIL_OR_PASSWORD_ERROR(-3002, "用户名或密码错误，请确认后再试")
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
