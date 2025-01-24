package com.rookie.stack.im.domain.entity.friendship;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 好友关系表，记录用户之间的好友关系及状态
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-29
 */
@Getter
@Setter
@TableName("im_friendship")
public class ImFriendship implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用 ID，区分多租户
     */
    @TableField("app_id")
    private Integer appId;

    /**
     * 发起方用户 ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 好友用户 ID
     */
    @TableField("friend_id")
    private Long friendId;

    /**
     * 备注信息
     */
    @TableField("remark")
    private String remark;

    /**
     * 好友状态: 1正常, 2删除, 3拉黑, 4删除+拉黑
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 同步序列号
     */
    @TableField("sequence")
    private Long sequence;
}
