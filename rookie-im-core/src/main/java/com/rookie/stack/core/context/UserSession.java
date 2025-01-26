package com.rookie.stack.core.context;

import lombok.Data;

/**
 * @Classname UserSession
 * @Description TODO
 * @Date 2025/1/26 11:10
 * @Created by liujiapeng
 */
@Data
public class UserSession {

    private String userId;

    private Integer appId;

    private Integer clientType;

    private Integer version;

    private Integer connectState;

    public enum ConnectState {
        ONLINE_STATUS(1),
        OFFLINE_STATUS(2);

        private Integer code;

        ConnectState(Integer code) {
            this.code = code;
        }
        public Integer getCode() {
            return code;
        }
    }
}
