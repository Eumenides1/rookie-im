CREATE TABLE platform_application (
      app_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '应用ID',
      app_name VARCHAR(100) NOT NULL COMMENT '应用名称',
      app_key VARCHAR(64) NOT NULL UNIQUE COMMENT '应用唯一标识（公开）',
      app_secret VARCHAR(255) NOT NULL COMMENT '应用密钥（私密）',
      app_owner BIGINT NOT NULL COMMENT '应用所有者的用户ID（主账号）',
      app_status TINYINT NOT NULL DEFAULT 1 COMMENT '应用状态：1-启用，2-禁用',
      callback_url VARCHAR(255) DEFAULT NULL COMMENT '回调URL（如登录/授权回调）',
      app_icon VARCHAR(255) DEFAULT NULL COMMENT '应用图标URL',
      description TEXT DEFAULT NULL COMMENT '应用描述',
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间'
);

CREATE TABLE application_permission (
    permission_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '权限点ID',
    app_id BIGINT NOT NULL COMMENT '应用ID',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
    permission_key VARCHAR(64) NOT NULL COMMENT '权限唯一标识',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
    FOREIGN KEY (app_id) REFERENCES platform_application(app_id) ON DELETE CASCADE
);

CREATE TABLE platform_role (
   role_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
   role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
   created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间'
);

CREATE TABLE platform_user_role (
    user_role_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    app_id BIGINT NOT NULL COMMENT '应用ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
    FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (app_id) REFERENCES platform_application(app_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES platform_role(role_id) ON DELETE CASCADE
);

CREATE TABLE platform_user_permission (
      user_permission_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
      user_id BIGINT NOT NULL COMMENT '用户ID',
      app_id BIGINT NOT NULL COMMENT '应用ID',
      permission_id BIGINT NOT NULL COMMENT '权限点ID',
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
      FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE,
      FOREIGN KEY (app_id) REFERENCES platform_application(app_id) ON DELETE CASCADE,
      FOREIGN KEY (permission_id) REFERENCES application_permission(permission_id) ON DELETE CASCADE
);

ALTER TABLE platform_role
    ADD COLUMN role_description VARCHAR(255) DEFAULT NULL COMMENT '角色中文描述';