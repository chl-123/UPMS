package com.dlu.upms.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String userAccount;

    private String userPassword;

    private Boolean rememberMe;
}
