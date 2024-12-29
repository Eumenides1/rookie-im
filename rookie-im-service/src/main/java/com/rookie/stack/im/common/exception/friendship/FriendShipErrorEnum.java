package com.rookie.stack.im.common.exception.friendship;

import com.rookie.stack.common.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Name：FriendShipErrorEnum
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@AllArgsConstructor
@Getter
public enum FriendShipErrorEnum implements ErrorEnum {
    ALREADY_FRIEND_SHIP(-6001, "你们已经是好友啦"),
    FRIEND_SHIP_BLACKED(-6002,"用户状态异常");

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
