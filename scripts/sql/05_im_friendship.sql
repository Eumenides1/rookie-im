CREATE TABLE `im_friendship` (
 `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键 ID',
 `app_id` INT NOT NULL COMMENT '应用 ID，区分多租户',
 `user_id` BIGINT NOT NULL COMMENT '发起方用户 ID',
 `friend_id` BIGINT NOT NULL COMMENT '好友用户 ID',
 `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注信息',
 `status` INT NOT NULL DEFAULT 1 COMMENT '好友状态: 1正常, 2删除, 3拉黑, 4删除+拉黑',
 `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 `sequence` BIGINT UNSIGNED DEFAULT NULL COMMENT '同步序列号',
 UNIQUE KEY `uq_app_user_friend` (`app_id`, `user_id`, `friend_id`) USING BTREE COMMENT '多租户环境下好友关系唯一约束',
 INDEX `idx_user_id` (`user_id`),
 INDEX `idx_friend_id` (`friend_id`),
 INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友关系表，记录用户之间的好友关系及状态';


CREATE TABLE `im_friendship_request` (
 `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键 ID',
 `app_id` INT NOT NULL COMMENT '应用 ID，区分多租户',
 `requester_id` BIGINT NOT NULL COMMENT '发起方用户 ID',
 `receiver_id` BIGINT NOT NULL COMMENT '接收方用户 ID',
 `read_status` INT NOT NULL DEFAULT 0 COMMENT '是否已读: 0未读, 1已读',
 `add_source` INT DEFAULT NULL COMMENT '好友来源（如手机号、二维码）',
 `add_wording` VARCHAR(255) DEFAULT NULL COMMENT '好友验证信息',
 `approve_status` INT NOT NULL DEFAULT 0 COMMENT '审批状态: 0待处理, 1同意, 2拒绝, 3撤回',
 `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 `sequence` BIGINT UNSIGNED DEFAULT NULL COMMENT '同步序列号',
 UNIQUE KEY `uq_app_request_receiver` (`app_id`, `requester_id`, `receiver_id`) USING BTREE COMMENT '应用内唯一好友申请',
 INDEX `idx_receiver_id` (`receiver_id`),
 INDEX `idx_approve_status` (`approve_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友请求表，记录用户的好友申请及状态';