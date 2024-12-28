package com.rookie.stack.platform.service;

import com.rookie.stack.platform.domain.bo.AccessKey;
import com.rookie.stack.platform.domain.req.PlatformUserLoginReq;
import com.rookie.stack.platform.domain.req.PlatformUserRegisterReq;
import com.rookie.stack.platform.domain.resp.LoginResp;

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
    LoginResp login(PlatformUserLoginReq loginReq);

    /**
     * 获取用户 ak sk
     */
    AccessKey getAccessKey();

    /**
     * 生成新的 ak,sk
     */
    AccessKey newAccessKey();

}
