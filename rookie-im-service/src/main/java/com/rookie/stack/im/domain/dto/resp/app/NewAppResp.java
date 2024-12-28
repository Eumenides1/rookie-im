package com.rookie.stack.im.domain.dto.resp.app;

import com.rookie.stack.im.domain.dto.base.BaseAppInfo;
import lombok.Data;

/**
 * Name：NewAppResp
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Data
public class NewAppResp extends BaseAppInfo {
    private Long appKey;
}
