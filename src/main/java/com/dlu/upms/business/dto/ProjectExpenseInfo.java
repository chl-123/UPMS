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
 * @since 2022-03-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectExpenseInfo{

    private String id;

    private String projectId;

    private Float siteCost;

    private Float teacherSalary;

    private Float other;

    private Float total;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String projectKey;

    private String projectName;

    private String userName;

    private String trainbaseId;

    private String baseName;

    private String isInsideBase;

    private String isInsideTeacher;
    private String teacherId;

}
