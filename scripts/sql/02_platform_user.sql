/**
  平台用户表
 */
CREATE TABLE platform_user (
   user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
   username VARCHAR(50) NOT NULL COMMENT '用户名',
   password VARCHAR(255) NOT NULL COMMENT '密码',
   email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
   phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
   account_type TINYINT NOT NULL COMMENT '账户类型：1-主账户，2-子账户',
   parent_user_id BIGINT DEFAULT NULL COMMENT '主账户ID',
   avatar_url VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
   gender TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
   status TINYINT DEFAULT 1 COMMENT '状态：1-启用，2-冻结',
   created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间'
);
/**
  平台用户信息表
 */
CREATE TABLE platform_user_profile (
  user_id BIGINT PRIMARY KEY COMMENT '用户ID',
  location VARCHAR(100) DEFAULT NULL COMMENT '地域信息',
  birthday DATE DEFAULT NULL COMMENT '生日',
  extra JSON DEFAULT NULL COMMENT '扩展字段（JSON格式）',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
  FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE
);
/**
  用户 ak sk 表
 */
CREATE TABLE platform_user_access_key (
 access_key_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
 user_id BIGINT NOT NULL COMMENT '用户ID',
 access_key VARCHAR(64) NOT NULL COMMENT 'AccessKey',
 secret_key VARCHAR(255) NOT NULL COMMENT '加密后的 SecretKey',
 status TINYINT DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
 FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE,
 UNIQUE KEY (access_key) COMMENT '确保 AccessKey 唯一'
);
/**
  用户子账户配额表
 */
CREATE TABLE platform_account_quota (
   quota_id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '配额ID',
   user_id          BIGINT NOT NULL COMMENT '用户ID',
   max_sub_accounts INT      DEFAULT 5 COMMENT '最大子账户数量',
   created_at       DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
   FOREIGN KEY (user_id) REFERENCES platform_user (user_id) ON DELETE CASCADE
);
/**
  平台用户登录日志表
 */
CREATE TABLE platform_user_login_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    login_ip VARCHAR(45) NOT NULL COMMENT '登录IP（IPv4或IPv6）',
    user_agent VARCHAR(255) DEFAULT NULL COMMENT '用户代理信息（浏览器、设备等）',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1-正常，2-异常',
    FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE
);
/**
    用户风险状态表
 */
CREATE TABLE platform_user_risk_status (
  user_id BIGINT PRIMARY KEY COMMENT '用户ID',
  last_login_ip VARCHAR(45) NOT NULL COMMENT '最近登录IP',
  last_login_time DATETIME NOT NULL COMMENT '最近登录时间',
  risk_level TINYINT DEFAULT 0 COMMENT '风险等级：0-正常，1-低风险，2-高风险',
  verify_required TINYINT DEFAULT 0 COMMENT '是否需要二次验证：0-否，1-是',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
  FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE
);

CREATE TABLE platform_operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    operation_type VARCHAR(50) NOT NULL COMMENT '操作类型（如发送验证码、验证验证码等）',
    operation_desc VARCHAR(255) NOT NULL COMMENT '操作描述',
    ip_address VARCHAR(50) DEFAULT NULL COMMENT '操作来源IP地址',
    status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '操作结果（0:失败, 1:成功）',
    error_message VARCHAR(255) DEFAULT NULL COMMENT '错误信息（如果有）',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    extra_data JSON DEFAULT NULL COMMENT '扩展字段，记录更多上下文信息',
    INDEX idx_user_id (user_id), -- 用户ID索引
    INDEX idx_operation_type (operation_type), -- 操作类型索引
    INDEX idx_created_at (created_at) -- 操作时间索引
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台操作日志表';
