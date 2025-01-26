package com.rookie.stack.core.context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @Classname UserClientDto
 * @Description TODO
 * @Date 2025/1/26 15:09
 * @Created by liujiapeng
 */
@Data
@AllArgsConstructor(staticName = "of") // 静态工厂方法
public class UserClientDto {

    private Integer appId;

    private Integer clientType;

    private String userId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserClientDto that = (UserClientDto) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(clientType, that.clientType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, userId, clientType);
    }

}
