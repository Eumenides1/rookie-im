package com.rookie.stack.im.domain.dto.req.app;

import com.rookie.stack.im.domain.dto.base.BaseAppInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Name：NewAppReq
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "新建应用请求体")
public class NewAppReq extends BaseAppInfo {
    private String apiSecret;
}
