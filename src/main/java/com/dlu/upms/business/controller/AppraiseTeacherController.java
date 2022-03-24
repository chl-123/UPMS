package com.dlu.upms.business.controller;


import com.dlu.upms.business.dto.*;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.base.ResponseConstant;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.dto.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.service.IAppraiseTeacherService;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/business/appraiseTeacher")
public class AppraiseTeacherController {
    @Autowired
    private IAppraiseTeacherService iAppraiseTeacherService;

    @GetMapping("/list")
    public PageResult<ProjectInfo> list(QueryProject project , Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                project.setTeacherId(userInfo.getId());
            }else {
                project.setFlag("1");
            }
        }
        System.out.println(project.toString());
        Page<ProjectInfo> pageInfo = iAppraiseTeacherService.selectProjectAppraiseListForTeacher(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id) {

        boolean result=iAppraiseTeacherService.deleteAppraiseTeacherInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateAppraiseTeacher appraiseTeacher, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo==null){
            return new Result<String>().error(ResponseConstant.NO_LOGIN);
        }
        boolean result=iAppraiseTeacherService.updateAppraiseTeacherInfo(appraiseTeacher);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateAppraiseTeacher appraiseTeacher, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("student".equals(userInfo.getRoleKey())){
                appraiseTeacher.setStudentId(userInfo.getId());
            }else {
                throw new BusinessException("你不是学生，不能选课");
            }
        }else {
            return new Result<String>().error(ResponseConstant.NO_LOGIN);
        }
        boolean result=iAppraiseTeacherService.createAppraiseTeacherInfo(appraiseTeacher);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
}
