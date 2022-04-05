package com.dlu.upms.system.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.xmSelect;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.Result;
import com.dlu.upms.common.excel.ExcelUtil;
import com.dlu.upms.common.util.Utils;
import com.dlu.upms.system.dto.*;
import com.dlu.upms.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/page/list")
    public PageResult<UserInfo> pageList(QueryUser user, Page<UserInfo> page) {
        Page<UserInfo> pageLog = iUserService.selectUserList(page, user);
        PageResult<UserInfo> pageResult = new PageResult<UserInfo>(pageLog.getTotal(), pageLog.getRecords());
        return pageResult;
    }
    @GetMapping("/list")
    public Result<List<xmSelect>> list(QueryUser user) {
        user.setRoleKey("teacher");
        List<UserInfo> ListInfo = iUserService.selectList(user);
        List<xmSelect> xmSelects=new ArrayList<>();
        if(!CollectionUtils.isEmpty(ListInfo)){
            for(UserInfo collegeInfo:ListInfo){
                if(collegeInfo!=null){
                    xmSelect xmSelect=new xmSelect();
                    xmSelect.setName(collegeInfo.getUserName());
                    xmSelect.setValue(collegeInfo.getId());
                    xmSelects.add(xmSelect);
                }
            }
        }

        return new Result<List<xmSelect>>().success().put(xmSelects);
    }
    @GetMapping("/delete/{id}/{userRoleId}")
    public Result<?> delete(@PathVariable("id")String id, @PathVariable("userRoleId")String userRoleId) {

        boolean result=iUserService.deleteUserInfo(id,userRoleId);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateUser user, HttpSession session, HttpServletRequest request) {
        boolean result=iUserService.updateUserInfo(user,session,request);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/changePwd")
    public Result<?> changePwd(@RequestBody UpdateUser user) {
        boolean result=iUserService.changePwd(user);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateUser user) {
        boolean result=iUserService.createUserInfo(user);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/batch")
    public Result<?> batchCreate(String flag, MultipartFile file) throws Exception {

        boolean result=iUserService.batchCreateUser(flag,file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @GetMapping("/export")
    public void export(QueryUser user, HttpServletResponse response) throws Exception {
        Map<String, String> userSex = new HashMap<>();
        Map<String, String> isInside = new HashMap<>();
        userSex.put("0", "男");
        userSex.put("1", "女");
        isInside.put("0","校内");
        isInside.put("1","校外");
        List<UserInfo> userInfos = iUserService.selectList(user);

        List<BatchCreateUser> batchCreateUserList=new ArrayList<>();
        for(UserInfo userInfo :userInfos){
            BatchCreateUser batchCreateUser =new BatchCreateUser();
            BeanUtils.copyProperties(userInfo,batchCreateUser);
            if(!Utils.isEmpty(userInfo.getUserSex())){
                batchCreateUser.setUserSex(userSex.get(userInfo.getUserSex()));
            }
            if(!Utils.isEmpty(userInfo.getIsInside())){
                batchCreateUser.setIsInside(isInside.get(userInfo.getIsInside()));
            }
            batchCreateUserList.add(batchCreateUser);
        }
        ExcelUtil.writeExcel(response,batchCreateUserList,"用户信息",BatchCreateUser.class);

    }
}
