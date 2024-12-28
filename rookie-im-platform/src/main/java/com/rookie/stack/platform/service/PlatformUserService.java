package com.rookie.stack.platform.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.rookie.stack.platform.domain.dto.bo.AccessKey;
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

    /**
     * 平台用户注册接口
     */
    void platformUserRegister(PlatformUserRegisterReq registerReq);

    /**
     * 平台用户登录接口
     */
    SaTokenInfo login(PlatformUserLoginReq loginReq);

    /**
     * 获取用户 ak sk
     */
    AccessKey getAccessKey();

}
