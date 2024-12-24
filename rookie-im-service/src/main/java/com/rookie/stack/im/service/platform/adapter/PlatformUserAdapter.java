package com.rookie.stack.im.service.platform.adapter;

import com.rookie.stack.im.domain.entity.platform.PlatformUser;
import com.rookie.stack.im.domain.enums.platform.PlatformUserStatusEnum;
import com.rookie.stack.im.domain.enums.platform.PlatformUserTypeEnum;
import com.rookie.stack.im.domain.vo.req.platform.PlatformUserRegisterReq;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Name：PlatformUserAdapter
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */
public class PlatformUserAdapter {

    public static PlatformUser buildRegisterUser(PlatformUserRegisterReq registerReq) {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setUsername(registerReq.getUsername());
        platformUser.setPassword(new BCryptPasswordEncoder().encode(registerReq.getPassword()));
        platformUser.setEmail(registerReq.getEmail());
        // 注册用户默认主账号
        platformUser.setAccountType(PlatformUserTypeEnum.MAIN_ACCOUNT.getStatus().byteValue());
        platformUser.setStatus(PlatformUserStatusEnum.UNFROZEN.getStatus().byteValue());
        return platformUser;
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password); // 使用 BCrypt 加密
    }
}
