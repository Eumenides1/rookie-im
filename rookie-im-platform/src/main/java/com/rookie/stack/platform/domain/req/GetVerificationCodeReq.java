package com.rookie.stack.platform.domain.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Name：GetVerificationCodeReq
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Data
public class GetVerificationCodeReq {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "不合法的邮箱格式")
    private String email;
}
