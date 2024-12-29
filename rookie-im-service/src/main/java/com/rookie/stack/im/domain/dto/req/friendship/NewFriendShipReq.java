package com.rookie.stack.im.domain.dto.req.friendship;

import com.rookie.stack.im.common.annotations.EnumValueValidator;
import com.rookie.stack.im.common.constants.constants.FriendShipConstants;
import com.rookie.stack.im.common.constants.enums.friendship.AddSourceEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Name：NewFriendShipReq
 * Author：eumenides
 * Created on: 2024/12/29
 * Description:
 */
@Data
public class NewFriendShipReq {
    @NotNull(message = "requesterId 不能为空")
    private Long requesterId;

    @NotNull(message = "receiverId 不能为空")
    private Long receiverId;

    @NotNull(message = "addSource 不能为空")
    @EnumValueValidator(enumClass = AddSourceEnum.class,enumField = "source")
    private Integer addSource;

    private String addWording = FriendShipConstants.COMMON_ADD_AWARDING;
}
