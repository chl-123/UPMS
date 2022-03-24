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
 * @since 2022-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_bus_project_expense")
public class ProjectExpense extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String projectId;

    private Float siteCost;

    private Float teacherSalary;

    private Float other;

    private Float total;


}
