package com.rookie.stack.im.domain.entity.group;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName im_group
 */
@TableName(value ="im_group")
@Data
public class ImGroup {
    /**
     * app_id
     */
    @TableId
    @TableField("app_id")
    private Integer appId;

    /**
     * group_id
     */
    @TableId
    @TableField("group_id")
    private Long groupId;

    /**
     * 群主
     */
    @TableField("owner_id")
    private Long ownerId;

    /**
     * 群类型 1私有群（类似微信） 2公开群(类似qq）
     */
    @TableField("group_type")
    private Integer groupType;

    /**
     * 
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 是否全员禁言，0 不禁言；1 全员禁言
     */
    @TableField("mute")
    private Integer mute;

    /**
     * 申请加群选项：0 禁止任何人申请加入，1 需要审批，2 允许自由加入
     */
    @TableField("apply_join_type")
    private Integer applyJoinType;

    /**
     * 
     */
    @TableField("photo")
    private String photo;

    /**
     * 
     */
    @TableField("max_member_count")
    private Integer maxMemberCount;

    /**
     * 群简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 群公告
     */
    @TableField("notification")
    private String notification;

    /**
     * 群状态 0正常 1解散
     */
    private Integer status;

    /**
     * 
     */
    private Long sequence;

    /**
     * 
     */
    @TableField("created_at")
    private Date createdAt;

    /**
     * 
     */
    @TableField("updated_at")
    private Date updatedAt;

    /**
     * 扩展信息
     */
    private Object extra;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ImGroup other = (ImGroup) that;
        return (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getOwnerId() == null ? other.getOwnerId() == null : this.getOwnerId().equals(other.getOwnerId()))
            && (this.getGroupType() == null ? other.getGroupType() == null : this.getGroupType().equals(other.getGroupType()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getMute() == null ? other.getMute() == null : this.getMute().equals(other.getMute()))
            && (this.getApplyJoinType() == null ? other.getApplyJoinType() == null : this.getApplyJoinType().equals(other.getApplyJoinType()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getMaxMemberCount() == null ? other.getMaxMemberCount() == null : this.getMaxMemberCount().equals(other.getMaxMemberCount()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
            && (this.getNotification() == null ? other.getNotification() == null : this.getNotification().equals(other.getNotification()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSequence() == null ? other.getSequence() == null : this.getSequence().equals(other.getSequence()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()))
            && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getOwnerId() == null) ? 0 : getOwnerId().hashCode());
        result = prime * result + ((getGroupType() == null) ? 0 : getGroupType().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getMute() == null) ? 0 : getMute().hashCode());
        result = prime * result + ((getApplyJoinType() == null) ? 0 : getApplyJoinType().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getMaxMemberCount() == null) ? 0 : getMaxMemberCount().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getNotification() == null) ? 0 : getNotification().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSequence() == null) ? 0 : getSequence().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", appId=").append(appId);
        sb.append(", groupId=").append(groupId);
        sb.append(", ownerId=").append(ownerId);
        sb.append(", groupType=").append(groupType);
        sb.append(", groupName=").append(groupName);
        sb.append(", mute=").append(mute);
        sb.append(", applyJoinType=").append(applyJoinType);
        sb.append(", photo=").append(photo);
        sb.append(", maxMemberCount=").append(maxMemberCount);
        sb.append(", introduction=").append(introduction);
        sb.append(", notification=").append(notification);
        sb.append(", status=").append(status);
        sb.append(", sequence=").append(sequence);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", extra=").append(extra);
        sb.append("]");
        return sb.toString();
    }
}