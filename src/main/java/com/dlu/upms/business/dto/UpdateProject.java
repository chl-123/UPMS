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
public class UpdateProject {


    private String id;

    private String teacherId;

    private String projectKey;

    private String projectName;

    private Integer selectNumMax;

    private Integer selectNum;

    private String collegeId;

    private String delFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projectStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  projectEnd;

    private String trainbaseId;


}
