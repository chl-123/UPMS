package com.dlu.upms.basicData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class xmSelect {
    private String name;
    private String value;
    private boolean selected;
}
