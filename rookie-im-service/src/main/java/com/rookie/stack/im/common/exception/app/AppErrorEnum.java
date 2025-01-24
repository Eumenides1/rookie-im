package com.rookie.stack.im.common.exception.app;

import com.rookie.stack.common.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Name：AppErrorEnum
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@AllArgsConstructor
@Getter
public enum AppErrorEnum implements ErrorEnum {
    APP_NAME_EXIST(-5001,"应用名称已存在"),
    ;
    private Integer code;
    private String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
