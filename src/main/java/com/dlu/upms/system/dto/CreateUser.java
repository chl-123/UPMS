package com.dlu.upms.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {
    private String id;

    private String userAccount;

    private String userPassword;

    private String userName;

    private String userEmail;

    private String userSex;

    private String userPhone;

    private String userStatus;

    private String roleName;
    private String roleId;
    private String roleKey;
    private String isInside;

    private String collegeId;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String delFlag;

    private String flag;
}
