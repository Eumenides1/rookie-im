package com.rookie.stack.im.common.exception.user;

import com.rookie.stack.common.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: 业务校验异常码
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-26
 */
@AllArgsConstructor
@Getter
public enum ImUserErrorEnum implements ErrorEnum {
    OUT_BOUND_MAX_IMPORT(-1001,"导入用户数量超过最大限制"),
    USER_STATUS_ERROR(-1002, "用户状态错误"),
    USER_NOT_EXISTS(-1003,"该用户不存在")
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
