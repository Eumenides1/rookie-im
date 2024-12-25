package com.rookie.stack.platform.domain.dto.req;


import com.rookie.stack.common.annotations.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Name：PlatformUserRegisterReq
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */
@Data
public class PlatformUserRegisterReq {

    @NotNull(message = "用户名不得为空")
    private String username;

    @NotNull(message = "密码不得为空")
    @StrongPassword
    private String password;

    @NotNull(message = "二次密码不得为空")
    private String retryPassword;

    @NotNull(message = "密码不得为空")
    @Email
    private String email;

    @NotNull(message = "验证码不得为空")
    private String verificationCode;
}
