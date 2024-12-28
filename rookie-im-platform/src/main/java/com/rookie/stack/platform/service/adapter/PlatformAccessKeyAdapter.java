package com.rookie.stack.platform.service.adapter;

import com.rookie.stack.platform.common.constants.enums.PlatformAccessKeyStatusEnum;
import com.rookie.stack.platform.domain.dto.bo.AccessKey;
import com.rookie.stack.platform.domain.entity.PlatformUserAccessKey;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Name：PlatformAccessKeyAdapter
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
public class PlatformAccessKeyAdapter {
    public static PlatformUserAccessKey buildPlatformUserAccessKey(Long userId, AccessKey accessKey) {
        PlatformUserAccessKey platformUserAccessKey = new PlatformUserAccessKey();
        platformUserAccessKey.setUserId(userId);
        platformUserAccessKey.setAccessKey(accessKey.getAccessKey());
        platformUserAccessKey.setSecretKey(new BCryptPasswordEncoder().encode(accessKey.getSecretKey()));
        platformUserAccessKey.setStatus(PlatformAccessKeyStatusEnum.UNFROZEN.getStatus().byteValue());
        return platformUserAccessKey;
    }
}
