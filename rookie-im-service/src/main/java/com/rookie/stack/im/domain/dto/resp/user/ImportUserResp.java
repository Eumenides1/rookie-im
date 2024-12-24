package com.rookie.stack.im.domain.dto.resp.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Name：ImportUserResp
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Data
@Schema(description = "批量导入用户返回数据")
public class ImportUserResp {
    @Schema(description = "导入成功的用户 ID")
    private List<Long> successUsers;
    @Schema(description = "导入失败的用户 ID")
    private List<Long> failUsers;
}
