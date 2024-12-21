package com.rookie.stack.im.domain.vo.req.user;

import com.rookie.stack.im.domain.vo.base.BaseUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Name：UpdateUserInfoReq
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "更新用户资料请求实体")
public class UpdateUserInfoReq extends BaseUserInfo {
    @Schema(description = "用户 ID")
    @NotNull(message = "用户 ID 不能为空")
    private Long userId;
}
