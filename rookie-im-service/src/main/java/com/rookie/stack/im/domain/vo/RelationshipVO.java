package com.rookie.stack.im.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Classname RelationshipVO
 * @Description TODO
 * @Date 2025/2/5 16:08
 * @Created by liujiapeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipVO {

    /**
     * 目标用户ID
     */
    private Long targetUserId;

    /**
     * 是否存在关系（当前用户是否添加了目标用户）
     */
    private Boolean exist;

    /**
     * 反向关系是否存在（目标用户是否添加了当前用户）
     */
    private Boolean reverseExist;

    /**
     * 关系状态
     * 0-非好友
     * 1-单向好友（当前用户添加了目标用户）
     * 2-双向好友
     */
    private Integer relationStatus;

    /**
     * 好友备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private LocalDateTime addTime;

    /**
     * 扩展字段（用于存储自定义关系属性）
     */
    private Map<String, Object> extraInfo;
}
