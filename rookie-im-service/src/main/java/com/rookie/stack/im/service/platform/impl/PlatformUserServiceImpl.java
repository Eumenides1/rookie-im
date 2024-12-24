package com.rookie.stack.im.service.platform.impl;

import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.platform.EmailServerErrorEnum;
import com.rookie.stack.im.common.exception.platform.PlatUserErrorEnum;
import com.rookie.stack.im.common.utils.AssertUtil;
import com.rookie.stack.im.common.utils.JwtUtils;
import com.rookie.stack.im.common.utils.RedisUtil;
import com.rookie.stack.im.dao.platform.PlatformUserDao;
import com.rookie.stack.im.domain.dto.req.platform.PlatformUserLoginReq;
import com.rookie.stack.im.domain.dto.resp.platform.PlatformUserLoginResp;
import com.rookie.stack.im.domain.entity.platform.PlatformUser;
import com.rookie.stack.im.domain.dto.req.platform.PlatformUserRegisterReq;
import com.rookie.stack.im.service.platform.PlatformUserService;
import com.rookie.stack.im.service.platform.adapter.PlatformUserAdapter;
import com.rookie.stack.push.MessagePushService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
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

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private PlatformUserDao platformUserDao;

    private static final String VERIFY_KEY_PREFIX = "email:verify:";
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRATION_SECONDS = 600; // 10分钟


    @Override
    public void sendVerificationCode(String email) {
        String verifyKey = VERIFY_KEY_PREFIX + email;
        // 先获取下有没有，用户超时了会重新获取，那就直接返回
        String storedCode = redisUtil.get(verifyKey);
        String verificationCode;
        if (storedCode != null) {
            verificationCode = storedCode;
        } else {
            // 生成验证码
            verificationCode = generateRandomCode();
            // 保存验证码到 Redis
            redisUtil.set(verifyKey, verificationCode, (long) CODE_EXPIRATION_SECONDS);
        }
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

    @Override
    public void platformUserRegister(PlatformUserRegisterReq registerReq) {
        // 验证验证码是否合法
        boolean verified = this.verifyCode(registerReq.getEmail(), registerReq.getVerificationCode());
        if (!verified) {
             throw new BusinessException(EmailServerErrorEnum.EMAIL_VERIFICATION_EXPIRED);
        }
        // 根据邮箱判断如果用户已经存在了，就直接返回
        PlatformUser byEmail = platformUserDao.getByEmail(registerReq.getEmail());
        AssertUtil.isEmpty(byEmail,"当前邮箱已注册；请更换邮箱～");
        // 验证密码是否匹配
        if (!Objects.equals(registerReq.getPassword(), registerReq.getRetryPassword())) {
            throw new BusinessException(PlatUserErrorEnum.PASSWORD_NOT_MATCH);
        }
        PlatformUser platformUser = PlatformUserAdapter.buildRegisterUser(registerReq);
        platformUserDao.save(platformUser);
    }

    @Override
    public PlatformUserLoginResp login(PlatformUserLoginReq loginReq) {
        // 根据邮箱判断如果用户已经存在了，就直接返回
        PlatformUser byEmail = platformUserDao.getByEmail(loginReq.getEmail());
        AssertUtil.isNotEmpty(byEmail,PlatUserErrorEnum.EMAIL_OR_PASSWORD_ERROR);

        // 校验密码
        if (!new BCryptPasswordEncoder().matches(loginReq.getPassword(), byEmail.getPassword())) {
            throw new BusinessException(PlatUserErrorEnum.EMAIL_OR_PASSWORD_ERROR);
        }

        // 4. 生成 Access Token 和 Refresh Token
        String accessToken = jwtUtils.generateAccessToken(byEmail.getUserId(), byEmail.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(byEmail.getUserId());

        return new PlatformUserLoginResp(accessToken, refreshToken,jwtUtils.getAccessTokenExpiration());
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < PlatformUserServiceImpl.CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
