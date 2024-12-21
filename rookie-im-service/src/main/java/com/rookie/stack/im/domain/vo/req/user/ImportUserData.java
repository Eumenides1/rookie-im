package com.rookie.stack.im.domain.vo.req.user;

import com.rookie.stack.im.domain.vo.resp.base.BaseUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Name：ImportUserData
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@Data
@Schema(description = "操作用户资料实体")
public class ImportUserData extends BaseUserInfo {
    private String password;
}
