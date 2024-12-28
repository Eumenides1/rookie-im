package com.rookie.stack.platform.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Name：AccessKey
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Data
@AllArgsConstructor
@Schema(description = "ak，sk 信息；安全信息，请妥善保存")
public class AccessKey {
    @Schema(description = "ak")
    private String accessKey;
    @Schema(description = "sk，首次获取可返回明文，二次获取返回为加密且脱敏信息")
    private String secretKey;
}
