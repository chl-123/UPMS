package com.dlu.upms.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.entity.RoleResource;
import com.dlu.upms.system.mapper.RoleResourceMapper;
import com.dlu.upms.system.service.IRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    public Set<String> listRoleResourceByUserId(String userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_PERMISSION);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = roleResourceMapper.listRoleResourceByUserId(userId);
        session.setAttribute(SystemConst.SYSTEM_USER_PERMISSION, set);
        return set;
    }
}
