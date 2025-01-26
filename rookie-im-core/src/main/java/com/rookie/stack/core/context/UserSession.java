package com.rookie.stack.core.context;

import cn.hutool.http.Header;
import com.rookie.stack.core.codec.pack.LoginPack;
import com.rookie.stack.core.codec.proto.MessageHeader;
import lombok.Builder;
import lombok.Data;

/**
 * @Classname UserSession
 * @Description TODO
 * @Date 2025/1/26 11:10
 * @Created by liujiapeng
 */
@Data
@Builder
public class UserSession {

    private String userId;

    private Integer appId;

    private Integer clientType;

    private Integer version;

    private Integer connectState;

    public static UserSession fromHeaderAndPack(MessageHeader header, LoginPack pack) {
        return UserSession.builder()
                .appId(header.getAppId())
                .clientType(header.getClientType())
                .userId(pack.getUserId())
                .connectState(UserSession.ConnectState.ONLINE_STATUS.getCode())
                .build();
    }

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
