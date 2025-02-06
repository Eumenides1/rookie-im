DROP TABLE IF EXISTS `im_group`;
CREATE TABLE `im_group` (
        `app_id` INT(20) NOT NULL COMMENT 'app_id',
        `group_id` BIGINT(20) NOT NULL COMMENT 'group_id',
        `owner_id` BIGINT(20) NOT NULL COMMENT '群主',
        `group_type` TINYINT(4) NULL DEFAULT NULL COMMENT '群类型 1私有群（类似微信） 2公开群(类似qq）',
        `group_name` VARCHAR(100) NULL DEFAULT NULL,
        `mute` TINYINT(1) NULL DEFAULT 0 COMMENT '是否全员禁言，0 不禁言；1 全员禁言',
        `apply_join_type` TINYINT(4) NULL DEFAULT NULL COMMENT '申请加群选项：0 禁止任何人申请加入，1 需要审批，2 允许自由加入',
        `photo` VARCHAR(300) NULL DEFAULT NULL,
        `max_member_count` INT(20) NULL DEFAULT NULL,
        `introduction` VARCHAR(500) NULL DEFAULT NULL COMMENT '群简介',
        `notification` VARCHAR(1000) NULL DEFAULT NULL COMMENT '群公告',
        `status` TINYINT(1) NULL DEFAULT 0 COMMENT '群状态 0正常 1解散',
        `sequence` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `extra` JSON NULL COMMENT '扩展信息',
        PRIMARY KEY (`app_id`, `group_id`),
        INDEX `idx_owner_id` (`owner_id`),
        INDEX `idx_group_type` (`group_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `im_group_member`;
CREATE TABLE `im_group_member` (
   `group_member_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
   `group_id` BIGINT(20) NOT NULL COMMENT 'group_id',
   `app_id` INT(20) NOT NULL,
   `member_id` BIGINT(20) NOT NULL COMMENT '成员id',
   `role` TINYINT(4) NULL DEFAULT 0 COMMENT '群成员类型，0 普通成员, 1 管理员, 2 群主， 3 禁言，4 已经移除的成员',
   `last_speak_time` BIGINT(20) NULL DEFAULT NULL,
   `is_muted` TINYINT(1) NULL DEFAULT 0 COMMENT '是否禁言，0 不禁言；1 禁言',
   `alias` VARCHAR(100) NULL DEFAULT NULL COMMENT '群昵称',
   `join_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `leave_time` DATETIME NULL DEFAULT NULL,
   `join_type` VARCHAR(50) NULL DEFAULT NULL COMMENT '加入类型',
   `extra` JSON NULL COMMENT '扩展信息',
   PRIMARY KEY (`group_member_id`),
   UNIQUE KEY `idx_group_member` (`group_id`, `member_id`),
   INDEX `idx_member_id` (`member_id`),
   INDEX `idx_role` (`role`),
   CONSTRAINT `fk_group_member_group` FOREIGN KEY (`app_id`, `group_id`) REFERENCES `im_group` (`app_id`, `group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;