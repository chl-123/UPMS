package com.dlu.upms.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlu.upms.common.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
@Data
@TableName("t_bus_project_select")
public class ProjectSelect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String studentId;

    private String projectId;

    private String teacherId;

    private String score;


}
