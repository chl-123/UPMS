package com.dlu.upms.basicData.entity;

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
@TableName("t_bd_trainbase")
public class Trainbase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String baseKey;

    private String baseName;

    private String baseAddress;

    private String isInside;


}
