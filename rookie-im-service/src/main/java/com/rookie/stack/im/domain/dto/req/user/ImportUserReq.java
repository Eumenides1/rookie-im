package com.rookie.stack.im.domain.dto.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Name：ImportUserReq
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Data
@Schema(description = "导入用户请求参数")
public class ImportUserReq {
    List<ImportUserData> userData;
}
