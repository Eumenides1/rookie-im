package com.rookie.stack.im.domain.dto.req.friendship;

import com.rookie.stack.common.domain.dto.req.PageBaseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Name：GetFriendshipRequestReq
 * Author：eumenides
 * Created on: 2025/1/1
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetFriendshipRequestReq extends PageBaseReq {
    private Long userId;
}
