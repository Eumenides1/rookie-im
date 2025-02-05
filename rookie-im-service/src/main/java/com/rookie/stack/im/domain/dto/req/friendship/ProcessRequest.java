package com.rookie.stack.im.domain.dto.req.friendship;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @Classname ProcessRequest
 * @Description 处理好友请求体
 * @Date 2025/2/5 11:36
 * @Created by liujiapeng
 */
@Data
public class ProcessRequest {
    @NotNull(message = "请求ID不能为空")
    private Long requestId;

    // TODO 仅作模拟，生产中用户id来自于token解析
    @NotNull(message = "请求用户ID")
    private Long userId;

    @NotNull(message = "操作类型不能为空")
    @Range(min = 1, max = 2, message = "操作类型不合法")
    private Integer action; // 1-同意 2-拒绝
}
