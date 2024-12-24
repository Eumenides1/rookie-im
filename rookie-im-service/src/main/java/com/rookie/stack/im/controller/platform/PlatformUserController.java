package com.rookie.stack.im.controller.platform;

import com.rookie.stack.im.common.annotations.SkipAppIdValidation;
import com.rookie.stack.im.domain.dto.req.platform.PlatformUserLoginReq;
import com.rookie.stack.im.domain.dto.req.platform.PlatformUserRegisterReq;
import com.rookie.stack.im.domain.dto.resp.base.ApiResult;
import com.rookie.stack.im.domain.dto.resp.platform.PlatformUserLoginResp;
import com.rookie.stack.im.service.platform.PlatformUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name：PlatformUserController
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@RestController
@RequestMapping("/platform_user")
@Tag(name = "【PLATFORM_USER】平台用户相关功能", description = "平台用户相关功能控制层")
@SkipAppIdValidation
public class PlatformUserController {

    @Resource
    private PlatformUserService platformUserService;

    @PostMapping("/register")
    @Operation(summary = "平台用户注册接口")
    public ApiResult<Void> register(@RequestBody @Valid PlatformUserRegisterReq registerReq) {
        platformUserService.platformUserRegister(registerReq);
        return ApiResult.success();
    }
    @PostMapping("/login")
    @Operation(summary = "平台用户登录接口")
    public ApiResult<PlatformUserLoginResp> login(@RequestBody @Valid PlatformUserLoginReq loginReq) {
        return ApiResult.success(platformUserService.login(loginReq));
    }

}
