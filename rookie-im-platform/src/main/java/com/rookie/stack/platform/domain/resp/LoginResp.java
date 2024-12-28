package com.rookie.stack.platform.domain.resp;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.rookie.stack.platform.domain.vo.FrontUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Name：LoginResp
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Data
@AllArgsConstructor
@Schema(description = "登录成功后用户信息")
public class LoginResp {
    @Schema(description = "用户资料")
    private FrontUserInfo user;
    @Schema(description = "用户 token 信息")
    private SaTokenInfo token;
}
