package com.rookie.stack.im.service.platform.impl;

import com.rookie.stack.im.common.utils.RedisUtil;
import com.rookie.stack.im.service.platform.PlatformUserService;
import com.rookie.stack.push.MessagePushService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

/**
 * Name：PlatformUserServiceImpl
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Service
public class PlatformUserServiceImpl implements PlatformUserService {

    @Resource
    private MessagePushService emailPushService;

    @Resource
    private RedisUtil redisUtil;

    private static final String VERIFY_KEY_PREFIX = "email:verify:";
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRATION_SECONDS = 600; // 10分钟

    @Override
    public void sendVerificationCode(String email) {
        String verifyKey = VERIFY_KEY_PREFIX + email;
        // 生成验证码
        String verificationCode = generateRandomCode(CODE_LENGTH);

        // 保存验证码到 Redis
        redisUtil.set(verifyKey, verificationCode, (long) CODE_EXPIRATION_SECONDS);

        // 发送邮件
        emailPushService.sendTemplate(
                email,
                "平台用户验证码",
                "verification-code.ftl",
                Map.of("user", email, "code", verificationCode)
        );
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String verifyKey = VERIFY_KEY_PREFIX + email;
        String storedCode = redisUtil.get(verifyKey);

        if (storedCode != null && storedCode.equals(code)) {
            redisUtil.delete(verifyKey); // 验证成功后删除验证码
            return true;
        }
        return false;
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
