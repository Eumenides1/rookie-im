package com.rookie.stack.im.domain.dto.resp.friendship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Name：FriendshipRequestData
 * Author：eumenides
 * Created on: 2025/1/1
 * Description: 用户请求
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipRequestData {

    private Long userId;

    private String nickName;

    private String photo;

    private String extra;

    private String addWording;

    private Integer approveStatus;

    private LocalDateTime createTime;

}
