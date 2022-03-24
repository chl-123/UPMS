package com.dlu.upms.basicData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
public class QueryTrainbase {
    private String id;

    private String baseKey;

    private String baseName;

    private String baseAddress;

    private String isInside;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;

    private String delFlag;


}
