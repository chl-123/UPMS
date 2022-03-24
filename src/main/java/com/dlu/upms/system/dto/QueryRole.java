package com.dlu.upms.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRole {
    private String id;

    private String roleName;

    private String roleKey;

    private String roleStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;

    private String delFlag;
}
