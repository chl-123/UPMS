package com.dlu.upms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import com.dlu.upms.system.dto.CreateUser;
import com.dlu.upms.system.dto.QueryUser;
import com.dlu.upms.system.dto.UpdateUser;
import com.dlu.upms.system.dto.UserInfo;
import com.dlu.upms.system.entity.Role;
import com.dlu.upms.system.entity.User;
import com.dlu.upms.system.entity.UserRole;
import com.dlu.upms.system.mapper.UserMapper;
import com.dlu.upms.system.service.IRoleService;
import com.dlu.upms.system.service.IUserRoleService;
import com.dlu.upms.system.service.IUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
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
 * @since 2022-01-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private IRoleService iRoleService;
    @Override
    public Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUser user) {
        Page<UserInfo> userInfoPage = userMapper.selectUserList(page, user);
        return  userInfoPage;
    }
    @Override
    public List<UserInfo> selectList(QueryUser user) {
        return userMapper.selectUserList(user);
    }


    @Override
    public boolean deleteUserInfo(String id, String userRoleId) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("id不能为为空");
        }
        if(Utils.isEmpty(userRoleId)){
            throw new BusinessException("用户角色不能为空");
        }
        boolean result = this.removeById(id);
        if(!result){
            throw new BusinessException("删除失败");
        }
        boolean result1 = iUserRoleService.removeById(userRoleId);
        if(!result1){
            throw new BusinessException("删除失败");
        }
        return true;
    }

    @Override
    public boolean updateUserInfo(UpdateUser user) {
        if(Utils.isEmpty(user.getId())){
            throw new BusinessException("Id不能为空");
        }
        if("3".equals(user.getFlag())){
            if(Utils.isEmpty(user.getUserRoleId())){
                throw new BusinessException("用户角色ID不能为空");
            }
        }

        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        this.checkInfo(userInfo);
        if("3".equals(user.getFlag())){
            UpdateWrapper<UserRole> userRoleUpdateWrapper=new UpdateWrapper<>();
            userRoleUpdateWrapper.eq("id",user.getUserRoleId());
            userRoleUpdateWrapper.set("role_id",user.getRoleId());
            userRoleUpdateWrapper.set("update_time",new Date());
            boolean result1 = iUserRoleService.update(userRoleUpdateWrapper);
            if(!result1){
                throw new BusinessException("更新失败");
            }
        }

        UpdateWrapper<User> userUpdateWrapper=new UpdateWrapper<>();
        userUpdateWrapper.eq("id",user.getId());
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
        boolean result2 = this.update(user1, userUpdateWrapper);
        return result2;

    }

    @Override
    public boolean createUserInfo(CreateUser user) {
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        this.checkInfo(userInfo);
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
        user1.setUserPassword("123456789");
        String password = new SimpleHash("md5", user1.getUserPassword(), user1.getUserAccount(), 6).toHex();
        user1.setUserPassword(password);
        user1.setUpdateTime(new Date());
        boolean result = this.save(user1);
        if(!result){
            throw new BusinessException("保存成功");
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_account",user.getUserAccount());
        List<User> list = this.list(queryWrapper);
        if(list.size()!=0){
            UserRole userRole =new UserRole();
            userRole.setUserId(list.get(0).getId());
            userRole.setUpdateTime(new Date());
            QueryWrapper<Role> roleQueryWrapper =new QueryWrapper<>();
            if("3".equals(user.getFlag())){
                userRole.setRoleId(user.getRoleId());
            }else  if("1".equals(user.getFlag())){

                roleQueryWrapper.eq("role_key","student");
                List<Role> list1 = iRoleService.list(roleQueryWrapper);
                if(list1.size()==0){
                    throw new BusinessException("保存失败");
                }
                userRole.setRoleId(list1.get(0).getId());

            }else  if("2".equals(user.getFlag())){
                roleQueryWrapper.eq("role_key","teacher");
                List<Role> list1 = iRoleService.list(roleQueryWrapper);
                if(list1.size()==0){
                    throw new BusinessException("保存失败");
                }
                userRole.setRoleId(list1.get(0).getId());
            }
            boolean result1 = iUserRoleService.save(userRole);
            if(!result1){
                throw new BusinessException("保存失败");
            }

        }else {
            throw new BusinessException("保存失败");
        }


        return true;
    }


    private void checkInfo(UserInfo user){
        if(Utils.isEmpty(user.getUserAccount())){
            throw new BusinessException("账号不能为空");
        }
        if(Utils.isEmpty(user.getUserName())){
            throw new BusinessException("用户名不能为空");
        }

        if("1".equals(user.getFlag())){
            if(Utils.isEmpty(user.getUserSex())){
                throw new BusinessException("性别不能为空");
            }
            if(Utils.isEmpty(user.getUserEmail())){
                throw new BusinessException("邮箱不能为空");
            }
            if(Utils.isEmpty(user.getUserPhone())){
                throw new BusinessException("手机号不能为空");
            }
            if(Utils.isEmpty(user.getCollegeId())){
                throw new BusinessException("学院ID不能为空");
            }
            if(Utils.isEmpty(user.getIsInside())){
                throw new BusinessException("是否校内不能为空");
            }
        }else if("3".equals(user.getFlag())){
            if(
                    Utils.isEmpty(user.getRoleId())
            ) {
                throw new BusinessException("角色ID不能为空");
            }
            if(
                    Utils.isEmpty(user.getUserStatus())
            ) {
                throw new BusinessException("用户状态不能为空");
            }
        }
    }
    public User queryUserByAccount(String userAccount) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.and(e -> e.eq("user_account", userAccount));
        return this.getOne(ew);
    }


}