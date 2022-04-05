package com.dlu.upms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.*;
import com.dlu.upms.business.service.IProjectExpenseService;
import com.dlu.upms.common.base.*;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/business/projectExpense")
public class ProjectExpenseController {
    @Autowired
    private IProjectExpenseService iProjectExpenseService;

    @GetMapping("/list")
    public PageResult<ProjectExpenseInfo> list(QueryProjectExpense project, Page<ProjectExpenseInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo==null){

            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectExpenseInfo> pageInfo = iProjectExpenseService.selectProjectExpenseList(page, project);
        PageResult<ProjectExpenseInfo> pageResult = new PageResult<ProjectExpenseInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }

    @PostMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id) {
        boolean result=iProjectExpenseService.deleteProjectExpenseInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateProjectExpense projectExpense, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo==null){
            return new Result<String>().error(ResponseConstant.NO_LOGIN);

        }
        boolean result=iProjectExpenseService.createProjectExpenseInfo(projectExpense);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateProjectExpense projectExpense, HttpSession session) {

        boolean result=iProjectExpenseService.updateProjectExpenseInfo(projectExpense);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }
}
