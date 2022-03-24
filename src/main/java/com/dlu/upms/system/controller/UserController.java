package com.dlu.upms.system.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.QueryTrainbase;
import com.dlu.upms.basicData.dto.TrainbaseInfo;
import com.dlu.upms.basicData.dto.xmSelect;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.Result;
import com.dlu.upms.system.dto.CreateUser;
import com.dlu.upms.system.dto.QueryUser;
import com.dlu.upms.system.dto.UpdateUser;
import com.dlu.upms.system.dto.UserInfo;
import com.dlu.upms.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public Result<?> update(@RequestBody UpdateUser user) {
        boolean result=iUserService.updateUserInfo(user);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
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
}
