package com.rookie.stack.platform.service.adapter;


import com.rookie.stack.platform.domain.dto.req.PlatformUserRegisterReq;
import com.rookie.stack.platform.domain.entity.PlatformUser;
import com.rookie.stack.platform.domain.enums.PlatformUserStatusEnum;
import com.rookie.stack.platform.domain.enums.PlatformUserTypeEnum;
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
}
