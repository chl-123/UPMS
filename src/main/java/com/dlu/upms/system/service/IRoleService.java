package com.dlu.upms.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.system.dto.CreateRole;
import com.dlu.upms.system.dto.QueryRole;
import com.dlu.upms.system.dto.RoleInfo;
import com.dlu.upms.system.dto.UpdateRole;
import com.dlu.upms.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
public interface IRoleService extends IService<Role> {

    Page<RoleInfo> selectRolePageList(Page<RoleInfo> page, QueryRole role);

    boolean deleteRoleInfo(String id);

    boolean updateRoleInfo(UpdateRole role);

    boolean createRoleInfo(CreateRole role);

    List<RoleInfo> selectRoleList(QueryRole role);
}
