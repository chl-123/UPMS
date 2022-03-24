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
@AllArgsConstructor
@NoArgsConstructor
public class AppraiseTeacherInfo{

    private String id;

    private String teacherId;

    private String projectId;

    private String studentId;

    private String teachingLevel;

    private String methodUseful;

    private String problem;

    private String rewards;

    private String opinions;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;


}
