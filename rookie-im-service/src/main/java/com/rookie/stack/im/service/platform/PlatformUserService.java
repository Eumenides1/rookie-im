package com.rookie.stack.im.service.platform;

import com.rookie.stack.im.domain.dto.req.platform.PlatformUserLoginReq;
import com.rookie.stack.im.domain.dto.req.platform.PlatformUserRegisterReq;
import com.rookie.stack.im.domain.dto.resp.platform.PlatformUserLoginResp;

public interface PlatformUserService {

    /**
     * 发送邮件验证码
     */
    void sendVerificationCode(String email);

    /**
     * 验证邮件验证码
     */
    boolean verifyCode(String email, String code);


    void platformUserRegister(PlatformUserRegisterReq registerReq);


    PlatformUserLoginResp login(PlatformUserLoginReq loginReq);

}
