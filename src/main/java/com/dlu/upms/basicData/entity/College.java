package com.dlu.upms.basicData.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlu.upms.common.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-09
 */
@Data
@TableName("t_bd_college")
public class College extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String collegeKey;

    private String collegeName;


}
