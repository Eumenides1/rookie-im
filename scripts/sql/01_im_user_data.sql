DROP TABLE IF EXISTS `im_user_data`;
CREATE TABLE `im_user_data`  (
     `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `app_id` int(11) NOT NULL,
     `nick_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
     `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `user_sex` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '性别（1:男，2:女，3:其他）',
     `birth_day` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
     `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
     `self_signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
     `friend_allow_type` int(10) NOT NULL DEFAULT 1 COMMENT '加好友验证类型（Friend_AllowType） 1无需验证 2需要验证',
     `forbidden_flag` int(10) NOT NULL DEFAULT 0 COMMENT '禁用标识 1禁用',
     `disable_add_friend` int(10) NOT NULL DEFAULT 0 COMMENT '管理员禁止用户添加加好友：0 未禁用 1 已禁用',
     `silent_flag` int(10) NOT NULL DEFAULT 0 COMMENT '禁言标识 1禁言',
     `user_type` int(10) NOT NULL DEFAULT 1 COMMENT '用户类型 1普通用户 2客服 3机器人',
     `del_flag` int(20) NOT NULL DEFAULT 0,
     `extra` JSON NULL COMMENT '扩展字段，存储 JSON 格式的数据',
     `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`app_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 删除主键
ALTER TABLE im_user_data DROP PRIMARY KEY;

-- 修改 user_id 字段类型为 BIGINT
ALTER TABLE im_user_data MODIFY user_id BIGINT NOT NULL;

-- 重新添加联合主键
ALTER TABLE im_user_data ADD PRIMARY KEY (app_id, user_id);


-- 新增手机号字段
ALTER TABLE im_user_data ADD COLUMN `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号';

-- 新增邮箱字段
ALTER TABLE im_user_data ADD COLUMN `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱';

alter table im_user_data
    modify del_flag int default 0 not null comment '用户状态：已删除 2；未删除 3';
