package com.rookie.stack.platform.domain.dto.bo;

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
public class AccessKey {
    private String accessKey;
    private String secretKey;
}
