package com.rookie.stack.im.domain.entity.platform;

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
@TableName("platform_user_risk_status")
public class PlatformUserRiskStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
      @TableId("user_id")
    private Long userId;

    /**
     * 最近登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 最近登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 风险等级：0-正常，1-低风险，2-高风险
     */
    @TableField("risk_level")
    private Byte riskLevel;

    /**
     * 是否需要二次验证：0-否，1-是
     */
    @TableField("verify_required")
    private Byte verifyRequired;

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
