package com.dlu.upms.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlu.upms.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_bus_appraise_student")
public class AppraiseStudent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String studentId;

    private String teacherId;

    private String projectId;

    private String learningStatus;

    private String jobStatus;

    private String problem;

    private String opinions;


}
