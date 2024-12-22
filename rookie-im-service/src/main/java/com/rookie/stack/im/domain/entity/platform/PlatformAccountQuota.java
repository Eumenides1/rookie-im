package com.rookie.stack.im.domain.entity.platform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-22
 */
@Getter
@Setter
@TableName("platform_account_quota")
public class PlatformAccountQuota implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配额ID
     */
      @TableId(value = "quota_id", type = IdType.AUTO)
    private Long quotaId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 最大子账户数量
     */
    @TableField("max_sub_accounts")
    private Integer maxSubAccounts;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 最近更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
