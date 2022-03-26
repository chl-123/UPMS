package com.dlu.upms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import com.dlu.upms.system.dto.CreateRole;
import com.dlu.upms.system.dto.QueryRole;
import com.dlu.upms.system.dto.RoleInfo;
import com.dlu.upms.system.dto.UpdateRole;
import com.dlu.upms.system.entity.Role;
import com.dlu.upms.system.mapper.RoleMapper;
import com.dlu.upms.system.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public Page<RoleInfo> selectRolePageList(Page<RoleInfo> page, QueryRole role) {
        Page<RoleInfo> roleInfoPage = roleMapper.selectRoleList(page,role);
        return  roleInfoPage;
    }

    @Override
    public boolean deleteRoleInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("id不能为空");
        }
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateRoleInfo(UpdateRole role) {
        if(Utils.isEmpty(role.getId())){
            throw new BusinessException("id不能为空");
        }
        if(Utils.isEmpty(role.getRoleKey())){
            throw new BusinessException("角色标记不能为空");
        }
        if(Utils.isEmpty(role.getRoleName())){
            throw new BusinessException("角色名称不能为空");
        }
        if(Utils.isEmpty(role.getRoleStatus())){
            throw new BusinessException("角色状态不能为空");
        }
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_key",role.getRoleKey()).or().eq("role_name",role.getRoleName());
        List<Role> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            for(Role info:list){
                if(info!=null){
                    if(role.getRoleKey().equals(info.getRoleKey())){
                        throw new BusinessException("角色标记在系统中已存在");
                    }
                    if(role.getRoleName().equals(info.getRoleName())){
                        throw new BusinessException("角色名称在系统中已存在");
                    }
                }
            }
        }
        Role updateRole=new Role();
        BeanUtils.copyProperties(role,updateRole);
        UpdateWrapper<Role> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",role.getId());
        boolean result = this.update(updateRole,updateWrapper);
        return result;
    }

    @Override
    public boolean createRoleInfo(CreateRole role) {
        if(Utils.isEmpty(role.getRoleKey())){
            throw new BusinessException("角色标记不能为空");
        }
        if(Utils.isEmpty(role.getRoleName())){
            throw new BusinessException("角色名称不能为空");
        }
        if(Utils.isEmpty(role.getRoleStatus())){
            throw new BusinessException("角色状态不能为空");
        }
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_key",role.getRoleKey()).or().eq("role_name",role.getRoleName());
        List<Role> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            for(Role info:list){
                if(info!=null){
                    if(role.getRoleKey().equals(info.getRoleKey())){
                        throw new BusinessException("角色标记在系统中已存在");
                    }
                    if(role.getRoleName().equals(info.getRoleName())){
                        throw new BusinessException("角色名称在系统中已存在");
                    }
                }
            }
        }
        Role info=new Role();
        BeanUtils.copyProperties(role,info);
        info.setUpdateTime(new Date());
        boolean result = this.save(info);
        return result;
    }

    @Override
    public List<RoleInfo> selectRoleList(QueryRole role) {
        List<RoleInfo> roleInfos = roleMapper.selectRoleList(role);
        return roleInfos;
    }
    public Role queryRoleByRoleKey(String roleKey) {
        QueryWrapper<Role> ew = new QueryWrapper<>();
        ew.and(e -> e.eq("role_key", roleKey));
        return this.getOne(ew);
    }
}
