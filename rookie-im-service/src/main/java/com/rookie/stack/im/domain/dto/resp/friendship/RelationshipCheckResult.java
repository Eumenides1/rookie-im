package com.rookie.stack.im.domain.dto.resp.friendship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname RelationshipCheckResult
 * @Description 好友关系校验返回参数
 * @Date 2025/2/5 15:59
 * @Created by liujiapeng
 */
@Data
public class RelationshipCheckResult {
    private List<SingleCheckResult> results;
    private String checkTimestamp = LocalDateTime.now().toString();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SingleCheckResult {
        private Long targetUserId;
        private Boolean isValid;
        private Integer relationStatus; // 0-非好友 1-单向好友 2-双向好友
        private String remark;          // 好友备注
        private LocalDateTime addTime;  // 添加时间
    }
}
