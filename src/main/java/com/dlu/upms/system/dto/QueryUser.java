package com.dlu.upms.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUser {
    private String id;

    private String userAccount;

    private String userPassword;

    private String userName;

    private String userEmail;

    private String userSex;

    private String userStatus;

    private String roleId;

    private String roleKey;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;

    private String delFlag;
}
