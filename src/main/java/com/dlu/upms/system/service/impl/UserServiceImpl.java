package com.dlu.upms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.basicData.entity.College;
import com.dlu.upms.basicData.service.ICollegeService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.excel.ExcelUtil;
import com.dlu.upms.common.util.Utils;
import com.dlu.upms.system.dto.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    @Autowired
    private ICollegeService iCollegeService;
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
        String password = new SimpleHash("md5", "123456789", user1.getUserAccount(), 6).toHex();
        user1.setUserPassword(password);
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

    @Override
    public boolean batchCreateUser(String flag, MultipartFile file) throws Exception {
        if(Utils.isEmpty(flag)){
            throw new BusinessException("标记不能为空");
        }
        String sheetName="";
        if ("1".equals(flag)) {
            sheetName="学生信息";
            this.batch(file,sheetName,"student");

        }else if("2".equals(flag)){
            sheetName="教师信息";
            this.batch(file,sheetName,"teacher");
        }
        return true;
    }
    private void batch(MultipartFile file,String sheetName,String roleKey) throws Exception {

        List<BatchCreateUser> dataList = ExcelUtil.read(file, BatchCreateUser.class, sheetName);
        if (dataList.size() == 0) {

            throw new BusinessException("【" + sheetName + "】这个Excel表没有数据");
        }
        Map<String, String> userSex = new HashMap<>();
        Map<String, String> isInside = new HashMap<>();
        userSex.put("男", "0");
        userSex.put("女", "1");
        isInside.put("校内", "0");
        isInside.put("校外", "1");
        Role role = iRoleService.queryRoleByRoleKey(roleKey);
        for (BatchCreateUser userInfo : dataList) {
            if (Utils.isEmpty(userInfo.getUserAccount())) {
                throw new BusinessException("账号不能为空");
            }
            if (Utils.isEmpty(userInfo.getUserName())) {
                throw new BusinessException("姓名不能为空");
            }
            if (Utils.isEmpty(userInfo.getCollegeName())) {
                throw new BusinessException("学院不能为空");
            }
            if (Utils.isEmpty(userInfo.getIsInside())) {
                throw new BusinessException("是否校内不能为空");
            } else {
                if (!"校内".equals(userInfo.getIsInside()) || !"校外".equals(userInfo.getIsInside())) {
                    throw new BusinessException("请填写【校内】或【校外】");
                }
            }
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userAccount", userInfo.getUserAccount());
            User queryUser = this.getOne(userQueryWrapper);
            if (queryUser != null) {
                continue;
            }
            User user = new User();
            BeanUtils.copyProperties(userInfo, user);
            if (!Utils.isEmpty(userInfo.getUserSex())) {
                if (!"男".equals(userInfo.getUserSex()) || !"女".equals(userInfo.getUserSex())) {
                    throw new BusinessException("请填写【男】或【女】");
                } else {
                    user.setUserSex(userSex.get(userInfo.getUserSex()));
                }
            }
            user.setIsInside(isInside.get(userInfo.getIsInside()));

            QueryWrapper<College> ew = new QueryWrapper<>();
            ew.and(e -> e.eq("college_name", userInfo.getCollegeName()));
            College college = iCollegeService.getOne(ew);
            if (college == null) {
                throw new BusinessException("学院信息填写错误");
            }
            user.setCollegeId(college.getId());
            String password = new SimpleHash("md5", "123456789", userInfo.getUserAccount(), 6).toHex();
            user.setUserPassword(password);
            user.setUserStatus("1");
            user.setUpdateTime(new Date());
            boolean result = this.save(user);
            if (!result) {
                throw new BusinessException("添加失败");
            }
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userInfo.getUserAccount());
            User user1 = this.getOne(userQueryWrapper);
            UserRole userRole =new UserRole();
            userRole.setUserId(user1.getId());
            userRole.setRoleId(role.getId());
            boolean result1 = iUserRoleService.save(userRole);
            if (!result1) {
                throw new BusinessException("添加失败");
            }

        }
    }


}