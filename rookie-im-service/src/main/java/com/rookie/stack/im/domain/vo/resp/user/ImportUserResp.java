package com.rookie.stack.im.domain.vo.resp.user;

import lombok.Data;

import java.util.List;

/**
 * Name：ImportUserResp
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@Data
public class ImportUserResp {
    private List<String> successUsers;
    private List<String> failUsers;
}
