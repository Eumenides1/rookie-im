package com.rookie.stack.platform.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Name：PlatformUserLoginResp
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */
@Data
@AllArgsConstructor
public class PlatformUserLoginResp {
    private String accessToken;
    private String refreshToken;
    private long expiresIn; // Access Token 的过期时间（秒）
}
