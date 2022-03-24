package com.dlu.upms.system.mapper;

import com.dlu.upms.system.entity.RoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    Set<String> listRoleResourceByUserId(String userId);
}
