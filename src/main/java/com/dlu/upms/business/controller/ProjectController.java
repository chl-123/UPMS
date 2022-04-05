package com.dlu.upms.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.xmSelect;
import com.dlu.upms.business.dto.*;
import com.dlu.upms.business.entity.Project;
import com.dlu.upms.business.service.IProjectService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.base.NoLoginException;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.Result;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/business/project")
public class ProjectController {
    @Autowired
    private IProjectService iProjectService;

    @GetMapping("/list")
    public PageResult<ProjectInfo> list(QueryProject project, Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                project.setTeacherId(userInfo.getId());
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectInfo> pageInfo = iProjectService.selectProjectList(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/projectList")
    public Result<List<xmSelect>> list(QueryProject project, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo==null){
            throw new NoLoginException("你还未登录请先登录");
        }
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        if("teacher".equals(userInfo.getRoleKey())){
            queryWrapper.eq("teacher_id",userInfo.getId());
        }
        List<Project> list = iProjectService.list(queryWrapper);
        List<xmSelect> dropdownList=new ArrayList<>();
        if(list.size()!=0){
            for(Project project1:list){
                xmSelect projectDropdown=new xmSelect();
                projectDropdown.setValue(project1.getId());
                projectDropdown.setName(project1.getProjectName());
                dropdownList.add(projectDropdown);
            }
        }
        return new Result<List<xmSelect>>().success().put(dropdownList);
    }

    @GetMapping("/appraiseTeacher/list")
    public PageResult<ProjectInfo> projectAppraiseTeacherList(QueryProject project, Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("student".equals(userInfo.getRoleKey())){
                project.setStudentId(userInfo.getId());
            }else {
                project.setFlag("1");
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectInfo> pageInfo = iProjectService.selectProjectAppraiseTeacherList(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/projectSelected/list")
    public PageResult<ProjectInfo> projectSelected(QueryProject project, Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("student".equals(userInfo.getRoleKey())){
                project.setStudentId(userInfo.getId());
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectInfo> pageInfo = iProjectService.selectProjectSelectedList(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/appraiseStudent/list")
    public PageResult<ProjectInfo> projectAppraiseStudentList(QueryProject project, Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                project.setTeacherId(userInfo.getId());
            }else {
                project.setFlag("1");
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectInfo> pageInfo = iProjectService.selectProjectAppraiseStudentList(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id) {

        boolean result=iProjectService.deleteProjectInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateProject project, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                project.setTeacherId(userInfo.getId());
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        boolean result=iProjectService.updateProjectInfo(project);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateProject project, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                project.setTeacherId(userInfo.getId());
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        boolean result=iProjectService.createProjectInfo(project);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @GetMapping("/studentScore/list")
    public PageResult<ProjectInfo> projectStudentScoreList(QueryProject project, Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                project.setTeacherId(userInfo.getId());
            }else {
                throw new BusinessException("你不是老师，不能选课");
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectInfo> pageInfo = iProjectService.selectStudentScoreList(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/studentScoreAppraise/list")
    public PageResult<ProjectInfo> selectStudentScoreAndAppraiseList(QueryProject project, Page<ProjectInfo> page, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("student".equals(userInfo.getRoleKey())){
                project.setStudentId(userInfo.getId());
            }else {
                project.setFlag("1");
            }
        }else {
            throw new NoLoginException("你还未登录请先登录");
        }
        Page<ProjectInfo> pageInfo = iProjectService.selectStudentScoreAndAppraiseList(page, project);
        PageResult<ProjectInfo> pageResult = new PageResult<ProjectInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
}
