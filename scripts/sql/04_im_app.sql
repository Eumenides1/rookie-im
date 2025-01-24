DROP TABLE if exists im_app;
CREATE TABLE im_app (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '应用 ID，主键（数据库内部使用）',
    app_key BIGINT NOT NULL COMMENT '应用唯一标识符（UUID 或雪花算法生成）',
    name VARCHAR(100) NOT NULL COMMENT '应用名称',
    icon_url VARCHAR(255) COMMENT '应用图标地址',
    type INT NOT NULL COMMENT '应用类型，如聊天、通知等',
    description TEXT COMMENT '应用描述',
    callback_url VARCHAR(255) COMMENT '回调地址，用于接收通知',
    api_key VARCHAR(64) NOT NULL COMMENT '应用的 API Key，用于身份验证',
    secret_key VARCHAR(64) NOT NULL COMMENT '应用的密钥，用于更安全的通信',
    status INT NOT NULL DEFAULT 1 COMMENT '状态，1 表示启用，0 表示禁用',
    config JSON COMMENT '应用配置，存储可扩展的配置信息',
    created_by BIGINT UNSIGNED COMMENT '创建人 ID',
    updated_by BIGINT UNSIGNED COMMENT '更新人 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uq_app_key (app_key) COMMENT '唯一标识符唯一约束'
) COMMENT='IM 应用表，保存应用的基础信息、安全字段及扩展配置';