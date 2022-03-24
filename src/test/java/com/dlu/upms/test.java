package com.dlu.upms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlu.upms.system.entity.User;
import com.dlu.upms.system.mapper.UserMapper;
import com.dlu.upms.system.service.IRoleResourceService;
import com.dlu.upms.system.service.IUserRoleService;
import com.dlu.upms.system.service.IUserService;
import com.dlu.upms.system.service.impl.UserServiceImpl;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;

public class test {
    @Autowired
    private static IUserService iUserService;
    @Autowired
    static  UserMapper userMapper;
    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private IRoleResourceService iRoleResourceService;
    public static void main(String[] args) {
        Object tokenCredentials = new SimpleHash("md5", "222222", "555555", 6).toHex();
        System.out.println(tokenCredentials.toString());
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
////        queryWrapper.eq("user_account", "chl");
//        User user = iUserService.getOne(queryWrapper);
//        System.out.println(user.toString());
        test1();
    }
    static void test1(){
        IUserService iUserService=new UserServiceImpl();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user.toString());
    }

}
