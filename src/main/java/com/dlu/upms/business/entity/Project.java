package com.dlu.upms.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlu.upms.common.entity.BaseEntity;
import lombok.Data;

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
@TableName("t_bus_project")

public class Project extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String teacherId;

    private String projectKey;

    private String projectName;

    private Integer selectNumMax;

    private Integer selectNum;

    private Date  projectStart;

    private Date  projectEnd;

    private String trainbaseId;

    private String collegeId;


}
