package com.dlu.upms.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlu.upms.common.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-01-05
 */
@Data
@TableName("t_sys_user")

public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userAccount;

    private String userPassword;

    private String userName;

    private String userEmail;

    private String userSex;

    private String userStatus;

    private String userPhone;

    private String isInside;

    private String collegeId;



}
