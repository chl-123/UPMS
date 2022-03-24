package com.dlu.upms.basicData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainbase {
    private String id;

    private String baseKey;

    private String baseName;

    private String baseAddress;

    private String isInside;

    private Date updateTime;

    private String delFlag;


}
