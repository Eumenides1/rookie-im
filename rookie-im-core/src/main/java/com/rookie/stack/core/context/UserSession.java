package com.rookie.stack.core.context;

import com.rookie.stack.core.codec.pack.LoginPack;
import com.rookie.stack.core.codec.proto.MessageHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UserSession
 * @Description 用户会话实体
 * @Date 2025/1/26 11:10
 * @Created by liujiapeng
 */
@Data
@Builder
@NoArgsConstructor  // ← 关键修复
@AllArgsConstructor // 如果已有全参构造需要保留
public class UserSession {

    private String userId;

    private Integer appId;

    private Integer clientType;

    private Integer version;

    private Integer connectState;

    private Long lastActiveTime;

    public static UserSession fromHeaderAndPack(MessageHeader header, LoginPack pack) {
        return UserSession.builder()
                .appId(header.getAppId())
                .clientType(header.getClientType())
                .userId(pack.getUserId())
                .connectState(UserSession.ConnectState.ONLINE_STATUS.getCode())
                .build();
    }

    public enum ConnectState {
        ONLINE_STATUS(0),
        RECONNECT(1),
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
