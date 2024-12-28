package com.rookie.stack.platform.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.rookie.stack.common.exception.BusinessException;
import com.rookie.stack.common.utils.AssertUtil;
import com.rookie.stack.platform.common.constants.enums.PlatformAccessKeyStatusEnum;
import com.rookie.stack.platform.common.exception.EmailServerErrorEnum;
import com.rookie.stack.platform.common.exception.PlatUserErrorEnum;
import com.rookie.stack.platform.common.utils.DesensitizationUtil;
import com.rookie.stack.platform.common.utils.RedisUtil;
import com.rookie.stack.platform.dao.PlatformUserAccessKeyDao;
import com.rookie.stack.platform.dao.PlatformUserDao;
import com.rookie.stack.platform.domain.bo.AccessKey;
import com.rookie.stack.platform.domain.entity.PlatformUser;
import com.rookie.stack.platform.domain.entity.PlatformUserAccessKey;
import com.rookie.stack.platform.domain.req.PlatformUserLoginReq;
import com.rookie.stack.platform.domain.req.PlatformUserRegisterReq;
import com.rookie.stack.platform.domain.resp.LoginResp;
import com.rookie.stack.platform.domain.vo.FrontUserInfo;
import com.rookie.stack.platform.service.PlatformUserService;
import com.rookie.stack.platform.service.adapter.PlatformAccessKeyAdapter;
import com.rookie.stack.platform.service.adapter.PlatformUserAdapter;
import com.rookie.stack.push.MessagePushService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
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

    private static final String VERIFY_KEY_PREFIX = "email:verify:";
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRATION_SECONDS = 600; // 10分钟

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_ACCESS_KEY_LENGTH = 32;
    private static final int DEFAULT_SECRET_KEY_LENGTH = 64;

    @Resource
    private MessagePushService emailPushService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private PlatformUserDao platformUserDao;

    @Resource
    private PlatformUserAccessKeyDao platformUserAccessKeyDao;

    @Resource
    private SecureRandom secureRandom;


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
    @Transactional
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

        // WARN 注册阶段不生成 AK SK
    }

    @Override
    public LoginResp login(PlatformUserLoginReq loginReq) {
        // 根据邮箱判断如果用户已经存在了，就直接返回
        PlatformUser byEmail = platformUserDao.getByEmail(loginReq.getEmail());
        AssertUtil.isNotEmpty(byEmail,PlatUserErrorEnum.EMAIL_OR_PASSWORD_ERROR);
        // 校验密码
        if (!new BCryptPasswordEncoder().matches(loginReq.getPassword(), byEmail.getPassword())) {
            throw new BusinessException(PlatUserErrorEnum.EMAIL_OR_PASSWORD_ERROR);
        }
        StpUtil.login(byEmail.getUserId());
        FrontUserInfo frontUserInfo = platformUserDao.getFrontUserInfo(byEmail.getUserId());
        return new LoginResp(frontUserInfo, StpUtil.getTokenInfo());
    }

    @Override
    public AccessKey getAccessKey() {
        long userId = StpUtil.getLoginIdAsLong();
        PlatformUserAccessKey userAccessKey = platformUserAccessKeyDao.getByUserId(userId);
        if (userAccessKey == null) {
            // 首次查询，或禁用现有 ak 后查询，则生成新 ak sk 并明文返回
            AccessKey accessKey = this.generateAccessKeys();
            userAccessKey = PlatformAccessKeyAdapter.buildPlatformUserAccessKey(userId, accessKey);
            platformUserAccessKeyDao.save(userAccessKey);
            return accessKey;
        }
        // 如果是首次生成后查询，则返回 ak 和脱敏后的 sk
        return new AccessKey(userAccessKey.getAccessKey(), DesensitizationUtil.desensitize(userAccessKey.getSecretKey(),3,3));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AccessKey newAccessKey() {
        long userId = StpUtil.getLoginIdAsLong();
        PlatformUserAccessKey userAccessKey = platformUserAccessKeyDao.getByUserId(userId);
        // 如果有可用 ak，那先将可用 ak 设置为不可用，然后生成新的保存
        if (userAccessKey != null) {
            userAccessKey.setStatus(PlatformAccessKeyStatusEnum.FREEZE.getStatus().byteValue());
            platformUserAccessKeyDao.updateById(userAccessKey);
        }
        // 生成新的 ak，返回
        AccessKey accessKey = this.generateAccessKeys();
        PlatformUserAccessKey newAccessKey = PlatformAccessKeyAdapter.buildPlatformUserAccessKey(userId, accessKey);
        platformUserAccessKeyDao.save(newAccessKey);
        return accessKey;
    }

    private AccessKey generateAccessKeys() {
        String accessKey = generateRandomString(DEFAULT_ACCESS_KEY_LENGTH);
        String secretKey = generateRandomString(DEFAULT_SECRET_KEY_LENGTH);
        return new AccessKey(accessKey, secretKey);
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
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
