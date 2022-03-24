package com.dlu.upms.system.entity;

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
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_resource")

public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String resourceName;

    private String parentId;

    private String resourceKey;

    private String resourceType;

    private String resourceIcon;

    private String resourcePath;

    private String resourceUrl;

    private Integer resourceLevel;

    private String resourceShow;

    private String resourceCache;

    private String resourcePageName;

    private String description;


}
