package com.rookie.stack.im.controller.platform;

import com.rookie.stack.im.common.annotations.RateLimiter;
import com.rookie.stack.im.common.annotations.RateLimiters;
import com.rookie.stack.im.domain.vo.resp.base.ApiResult;
import com.rookie.stack.im.service.platform.PlatformUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name：EmailVerificationController
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */

@RestController
@RequestMapping("/platform_email")
@Tag(name = "【PLATFORM_EMAIL】平台邮箱验证相关功能", description = "平台邮箱验证相关功能")
public class EmailVerificationController {

    @Resource
    private PlatformUserService platformUserService;

    /**
     * 获取邮箱验证码
     */
    @PostMapping("/get-email-verification-code")
    @RateLimiters({
            @RateLimiter(type = "IP", limit = 10, duration = 60), // IP 限流：每分钟最多 10 次
            @RateLimiter(type = "EMAIL", limit = 1, duration = 60) // 邮箱限流：每分钟最多 1 次
    })
    @Operation(summary = "获取邮箱验证码接口")
    public ApiResult<String> getEmailVerificationCode(@RequestParam String email) {
        platformUserService.sendVerificationCode(email);
        return ApiResult.success("验证码已发送，请检查邮箱");
    }
    /**
     * 验证邮箱验证码
     */
    @PostMapping("/verify-email-code")
    @Operation(summary = "验证邮箱验证码")
    public ApiResult<Boolean> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = platformUserService.verifyCode(email, code);
        return ApiResult.success(isValid);
    }
}
