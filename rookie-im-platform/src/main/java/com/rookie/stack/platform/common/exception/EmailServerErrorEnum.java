package com.rookie.stack.platform.common.exception;

import com.rookie.stack.common.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Name：EmailServerErrorEnum
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@AllArgsConstructor
@Getter
public enum EmailServerErrorEnum implements ErrorEnum {
    VERIFIED_CODE_OUT_LIMIT(-2001, "获取验证码过于频繁！请稍后再试"),
    EMAIL_VERIFICATION_EXPIRED(-2002, "验证码已过期，请重新获取")
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
