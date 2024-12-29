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
 * 好友请求表，记录用户的好友申请及状态
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-29
 */
@Getter
@Setter
@TableName("im_friendship_request")
public class ImFriendshipRequest implements Serializable {

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
    @TableField("requester_id")
    private Long requesterId;

    /**
     * 接收方用户 ID
     */
    @TableField("receiver_id")
    private Long receiverId;

    /**
     * 是否已读: 0未读, 1已读
     */
    @TableField("read_status")
    private Integer readStatus;

    /**
     * 好友来源（如手机号、二维码）
     */
    @TableField("add_source")
    private Integer addSource;

    /**
     * 好友验证信息
     */
    @TableField("add_wording")
    private String addWording;

    /**
     * 审批状态: 0待处理, 1同意, 2拒绝, 3撤回
     */
    @TableField("approve_status")
    private Integer approveStatus;

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
