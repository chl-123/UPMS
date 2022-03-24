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
 * @since 2022-02-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCollege {

    private String id;

    private String collegeKey;

    private String collegeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;


}
