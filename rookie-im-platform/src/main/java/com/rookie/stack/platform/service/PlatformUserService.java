package com.rookie.stack.platform.service;


import cn.dev33.satoken.stp.SaTokenInfo;
import com.rookie.stack.platform.domain.dto.req.PlatformUserLoginReq;
import com.rookie.stack.platform.domain.dto.req.PlatformUserRegisterReq;

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


    SaTokenInfo login(PlatformUserLoginReq loginReq);

}
