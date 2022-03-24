package com.dlu.upms.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
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
public class AppraiseStudentInfo{

    private String id;

    private String studentId;

    private String teacherId;

    private String projectId;

    private String learningStatus;

    private String jobStatus;

    private String problem;

    private String opinions;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

}
