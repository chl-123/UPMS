package com.dlu.upms.system.service;

import com.dlu.upms.system.entity.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
public interface IRoleResourceService extends IService<RoleResource> {
    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    Set<String> listRoleResourceByUserId(String userId);
}
