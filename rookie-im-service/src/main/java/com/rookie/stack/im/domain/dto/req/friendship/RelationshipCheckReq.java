package com.rookie.stack.im.domain.dto.req.friendship;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Classname RelationshipCheckReq
 * @Description 好友关系校验接口入参
 * @Date 2025/2/5 15:56
 * @Created by liujiapeng
 */
@Data
public class RelationshipCheckReq {

    @NotNull(message = "校验用户id不能为空")
    private Long userId;

    @NotEmpty(message = "目标用户不能为空")
    @Size(max = 100, message = "单次最多查询100个用户")
    private List<Long> targetUserIds;

    @NotNull(message = "校验模式不能为空")
    private CheckMode checkMode = CheckMode.BIDIRECTIONAL;

    // 扩展校验条件（可选）
    private Map<String, Object> extraConditions;

    public enum CheckMode {
        UNIDIRECTIONAL,  // 仅校验发起方是否添加目标方
        BIDIRECTIONAL,   // 校验双方互为好友
        BLACKLIST_CHECK, // 黑名单校验（扩展）
        GROUP_RELATION   // 群组关系校验（扩展）
    }
}


