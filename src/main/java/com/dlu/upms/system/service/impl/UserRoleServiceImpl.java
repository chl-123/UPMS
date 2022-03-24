package com.dlu.upms.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.entity.UserRole;
import com.dlu.upms.system.mapper.UserRoleMapper;
import com.dlu.upms.system.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public Set<String> listUserRoleByUserId(String userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_ROLE);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = userRoleMapper.listUserRoleByUserId(userId);
        session.setAttribute(SystemConst.SYSTEM_USER_ROLE, set);
        return set;
    }
}
