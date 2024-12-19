package com.rookie.stack.im.domain.vo.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Name：ImportUserData
 * Author：eumenides
 * Created on: 2024/12/19
 * Description:
 */
@Data
@Schema(description = "操作用户资料实体")
public class ImportUserData {
    private String nickName;
    private String password;
    private String photo;
    private Integer userSex;
    private String phone;
    private String email;
    private String birthDay;
    private String location;
    private String selfSignature;
    private Integer friendAllowType;
    private Integer disableAddFriend;
    private Integer userType;
    private String extra;  // 存储 JSON 数据
}
