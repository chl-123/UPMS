package com.dlu.upms.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRole {
    private String id;

    private String roleName;

    private String roleKey;

    private String roleStatus;


    private String delFlag;
}
