package com.dlu.upms.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {
    private String id;

    private String roleName;

    private String roleKey;

    private String roleStatus;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String delFlag;
}
