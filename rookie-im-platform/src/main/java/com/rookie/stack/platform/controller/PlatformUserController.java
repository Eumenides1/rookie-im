package com.rookie.stack.platform.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.rookie.stack.common.domain.dto.resp.ApiResult;

import com.rookie.stack.platform.domain.dto.req.PlatformUserLoginReq;
import com.rookie.stack.platform.domain.dto.req.PlatformUserRegisterReq;
import com.rookie.stack.platform.service.PlatformUserService;
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
    public ApiResult<SaTokenInfo> login(@RequestBody @Valid PlatformUserLoginReq loginReq) {
        SaTokenInfo login = platformUserService.login(loginReq);
        return ApiResult.success(login);
    }
}
