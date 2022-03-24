package com.dlu.upms.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRole {

    private String roleName;

    private String roleKey;

    private String roleStatus;

}
