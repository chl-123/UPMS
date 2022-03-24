package com.dlu.upms.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dlu.upms.basicData.dto.xmSelect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class ProjectInfo{

    private String id;
    private String studentName;
    private String teacherId;
    private String studentId;

    private String userName;
    private String collegeName;
    private String projectKey;

    private String projectName;

    private Integer selectNumMax;

    private Integer selectNum;

    private String collegeId;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String isSelect;

    private String projectSelectId;

    private List<xmSelect> collegeList=new ArrayList<>();

    @JSONField(format = "yyyy-MM-dd")
    private Date  projectStart;
    @JSONField(format = "yyyy-MM-dd")
    private Date  projectEnd;
    @JSONField(format = "yyyy-MM-dd")
    private Date  appraiseTime;

    private String trainbaseId;

    private String baseName;

    private String projectId;

    private String learningStatus;

    private String jobStatus;

    private String problem;

    private String opinions;

    private String teachingLevel;

    private String methodUseful;

    private String rewards;

    private String appraiseTeacherId;

    private String appraiseStudentId;

    private String userAccount;

    private String isInside;

    private String userEmail;

    private String userSex;

    private String userPhone;

    private String userStatus;

    private String score;



}
