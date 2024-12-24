package com.rookie.stack.im.domain.dto.req.platform;

import com.rookie.stack.im.common.annotations.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Name：PlatformUserLoginReq
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */
@Data
public class PlatformUserLoginReq {

    @NotNull(message = "邮箱不能为空")
    @Email
    private String email;

    @NotNull
    @StrongPassword(message = "参数异常")
    private String password;

}
