package com.dlu.upms.system.service;

import com.dlu.upms.system.entity.UserRole;
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
public interface IUserRoleService extends IService<UserRole> {
    public Set<String> listUserRoleByUserId(String userId);
}
