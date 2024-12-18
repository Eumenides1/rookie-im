package com.rookie.stack.im.domain.vo.req.user;

import com.rookie.stack.im.domain.entity.ImUserData;
import com.rookie.stack.im.domain.vo.req.BaseRequest;
import lombok.Data;

import java.util.List;

/**
 * Name：ImportUserReq
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Data
public class ImportUserReq extends BaseRequest {
    List<ImUserData> userData;
}
