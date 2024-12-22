package com.rookie.stack.im.service.platform;

public interface PlatformUserService {

    /**
     * 发送邮件验证码
     * @param email
     */
    void sendVerificationCode(String email);

    /**
     * 验证邮件验证码
     * @param email
     * @param code
     * @return
     */
    boolean verifyCode(String email, String code);

}
