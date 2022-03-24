package com.dlu.upms.business.dto;

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
 * @since 2022-02-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryProject {


    private String id;

    private String teacherId;
    private String studentId;
    private String projectKey;

    private String projectName;

    private Integer selectNumMax;

    private Integer selectNum;

    private String collegeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  projectStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  projectEnd;

    private String trainbaseId;

    private String flag;


}
