package com.rookie.stack.im.domain.entity.group;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName im_group_member
 */
@TableName(value ="im_group_member")
@Data
public class ImGroupMember {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @TableField("group_member_id")
    private Long groupMemberId;

    /**
     * group_id
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 
     */
    @TableField("app_id")
    private Integer appId;

    /**
     * 成员id
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 群成员类型，0 普通成员, 1 管理员, 2 群主， 3 禁言，4 已经移除的成员
     */
    private Integer role;

    /**
     * 
     */
    @TableField("last_speak_time")
    private Long lastSpeakTime;

    /**
     * 是否禁言，0 不禁言；1 禁言
     */
    @TableField("is_muted")
    private Integer isMuted;

    /**
     * 群昵称
     */
    private String alias;

    /**
     * 
     */
    @TableField("join_time")
    private Date joinTime;

    /**
     * 
     */
    @TableField("leave_time")
    private Date leaveTime;

    /**
     * 加入类型
     */
    @TableField("join_type")
    private String joinType;

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
        ImGroupMember other = (ImGroupMember) that;
        return (this.getGroupMemberId() == null ? other.getGroupMemberId() == null : this.getGroupMemberId().equals(other.getGroupMemberId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getLastSpeakTime() == null ? other.getLastSpeakTime() == null : this.getLastSpeakTime().equals(other.getLastSpeakTime()))
            && (this.getIsMuted() == null ? other.getIsMuted() == null : this.getIsMuted().equals(other.getIsMuted()))
            && (this.getAlias() == null ? other.getAlias() == null : this.getAlias().equals(other.getAlias()))
            && (this.getJoinTime() == null ? other.getJoinTime() == null : this.getJoinTime().equals(other.getJoinTime()))
            && (this.getLeaveTime() == null ? other.getLeaveTime() == null : this.getLeaveTime().equals(other.getLeaveTime()))
            && (this.getJoinType() == null ? other.getJoinType() == null : this.getJoinType().equals(other.getJoinType()))
            && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGroupMemberId() == null) ? 0 : getGroupMemberId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getLastSpeakTime() == null) ? 0 : getLastSpeakTime().hashCode());
        result = prime * result + ((getIsMuted() == null) ? 0 : getIsMuted().hashCode());
        result = prime * result + ((getAlias() == null) ? 0 : getAlias().hashCode());
        result = prime * result + ((getJoinTime() == null) ? 0 : getJoinTime().hashCode());
        result = prime * result + ((getLeaveTime() == null) ? 0 : getLeaveTime().hashCode());
        result = prime * result + ((getJoinType() == null) ? 0 : getJoinType().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupMemberId=").append(groupMemberId);
        sb.append(", groupId=").append(groupId);
        sb.append(", appId=").append(appId);
        sb.append(", memberId=").append(memberId);
        sb.append(", role=").append(role);
        sb.append(", lastSpeakTime=").append(lastSpeakTime);
        sb.append(", isMuted=").append(isMuted);
        sb.append(", alias=").append(alias);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", leaveTime=").append(leaveTime);
        sb.append(", joinType=").append(joinType);
        sb.append(", extra=").append(extra);
        sb.append("]");
        return sb.toString();
    }
}