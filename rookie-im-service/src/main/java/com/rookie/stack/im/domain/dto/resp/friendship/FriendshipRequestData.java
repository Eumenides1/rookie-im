package com.rookie.stack.im.domain.dto.resp.friendship;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Name：FriendshipRequestData
 * Author：eumenides
 * Created on: 2025/1/1
 * Description: 用户请求
 */
@Data
public class FriendshipRequestData {

    private Integer userId;

    private String nickName;

    private String photo;

    private String extra;

    private Integer addWording;

    private Integer approveStatus;

    private LocalDateTime createTime;

}
