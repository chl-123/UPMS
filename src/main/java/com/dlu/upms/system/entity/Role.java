package com.dlu.upms.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlu.upms.common.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
@Data
@TableName("t_sys_role")

public class Role extends BaseEntity {

//    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    @TableField("role_name")
    private String roleName;
    @TableField("role_key")
    private String roleKey;
    @TableField("role_status")
    private String roleStatus;


}
