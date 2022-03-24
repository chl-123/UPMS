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
 * @since 2022-03-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryAppraiseStudent {

    private String id;

    private String studentId;

    private String teacherId;

    private String projectId;

    private String learningStatus;

    private String jobStatus;

    private String problem;

    private String opinions;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;
}
