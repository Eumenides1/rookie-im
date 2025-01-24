package com.rookie.stack.platform.domain.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * Name：FrontUserInfo
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Data
public class FrontUserInfo {

    private Long userId;
    private String username;
    private String email;
    private String phone;
    private Integer accountType;
    private Long parentUserId;
    private String avatarUrl;
    private Integer gender;
    private String location;
    private LocalDate birthday;
    private String extra;

}
