package com.dlu.upms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.system.dto.QueryRole;
import com.dlu.upms.system.dto.RoleInfo;
import com.dlu.upms.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
public interface RoleMapper extends BaseMapper<Role> {
    Page<RoleInfo> selectRoleList(Page<RoleInfo> page, @Param("role") QueryRole role);
    List<RoleInfo> selectRoleList(@Param("role") QueryRole role);

}
