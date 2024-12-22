package com.rookie.stack.im.domain.entity.platform;

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
 * 平台操作日志表
 * </p>
 *
 * @author Jaguarliu
 * @since 2024-12-22
 */
@Getter
@Setter
@TableName("platform_operation_log")
public class PlatformOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作类型（如发送验证码、验证验证码等）
     */
    @TableField("operation_type")
    private String operationType;

    /**
     * 操作描述
     */
    @TableField("operation_desc")
    private String operationDesc;

    /**
     * 操作来源IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 操作结果（0:失败, 1:成功）
     */
    @TableField("status")
    private Boolean status;

    /**
     * 错误信息（如果有）
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 操作时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 扩展字段，记录更多上下文信息
     */
    @TableField("extra_data")
    private String extraData;
}
