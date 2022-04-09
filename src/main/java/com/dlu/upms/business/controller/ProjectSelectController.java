package com.dlu.upms.business.controller;


import com.dlu.upms.business.dto.Charts;
import com.dlu.upms.business.dto.CreateProjectSelect;
import com.dlu.upms.business.dto.UpdateProjectSelect;
import com.dlu.upms.business.entity.ProjectSelect;
import com.dlu.upms.business.service.IProjectSelectService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.base.NoLoginException;
import com.dlu.upms.common.base.ResponseConstant;
import com.dlu.upms.common.base.Result;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/business/projectSelect")
public class ProjectSelectController {
    @Autowired
    private IProjectSelectService iProjectSelectService;

    @PostMapping("/delete")
    public Result<?> delete(@RequestBody CreateProjectSelect projectSelect, HttpSession session) {

        boolean result=iProjectSelectService.deleteProjectSelectInfo(projectSelect);
        if (result) {
            return new Result<>().success("退选成功");

        }else {
            return new Result<>().error("退选失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateProjectSelect projectSelect, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("student".equals(userInfo.getRoleKey())){
                projectSelect.setStudentId(userInfo.getId());
            }else {
                throw new BusinessException("你不是学生，不能选课");
            }
        }else {
            return new Result<String>().error(ResponseConstant.NO_LOGIN);
        }
        boolean result=iProjectSelectService.createProjectSelectInfo(projectSelect);
        if (result) {
            return new Result<>().success("选择成功");

        }else {
            return new Result<>().error("选择失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateProjectSelect projectSelect, HttpSession session) {

        boolean result=iProjectSelectService.updateProjectSelectInfo(projectSelect);
        if (result) {
            return new Result<>().success("选择成功");

        }else {
            return new Result<>().error("选择失败，请重试");
        }
    }
    @GetMapping("/selectCount")
    public Result<List<Charts>> selectCount(HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo==null){
            throw new NoLoginException("你还未登录请先登录");
        }
        ProjectSelect projectSelect=new ProjectSelect();
        if("teacher".equals(userInfo.getRoleKey())){
            projectSelect.setTeacherId(userInfo.getId());
        }
        List<Charts> result=iProjectSelectService.selectCount(projectSelect);
        return new Result<List<Charts>>().success().put(result);
    }
    @GetMapping("/selectScore")
    public Result<List<Charts>> selectScore(ProjectSelect projectSelect) {

        List<Charts> result=iProjectSelectService.selectScore(projectSelect);
        return new Result<List<Charts>>().success().put(result);
    }
    @GetMapping("/selectScoreCount")
    public Result<List<Charts>> selectScoreCount(ProjectSelect projectSelect) {

        List<Charts> result=iProjectSelectService.selectScoreCount(projectSelect);
        return new Result<List<Charts>>().success().put(result);
    }

}
