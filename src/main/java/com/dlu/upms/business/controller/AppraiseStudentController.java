package com.dlu.upms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.AppraiseStudentInfo;
import com.dlu.upms.business.dto.CreateAppraiseStudent;
import com.dlu.upms.business.dto.QueryAppraiseStudent;
import com.dlu.upms.business.dto.UpdateAppraiseStudent;
import com.dlu.upms.business.service.IAppraiseStudentService;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.ResponseConstant;
import com.dlu.upms.common.base.Result;
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
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/business/appraiseStudent")
public class AppraiseStudentController {
    @Autowired
    private IAppraiseStudentService iAppraiseStudentService;

    @GetMapping("/list")
    public PageResult<AppraiseStudentInfo> list(QueryAppraiseStudent appraiseStudent, Page<AppraiseStudentInfo> page, HttpSession session) {
        Page<AppraiseStudentInfo> pageInfo = iAppraiseStudentService.selectAppraiseStudentList(page, appraiseStudent);
        PageResult<AppraiseStudentInfo> pageResult = new PageResult<AppraiseStudentInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id) {

        boolean result=iAppraiseStudentService.deleteAppraiseStudentInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateAppraiseStudent appraiseStudent, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo==null){
            return new Result<String>().error(ResponseConstant.NO_LOGIN);

        }
        boolean result=iAppraiseStudentService.updateAppraiseStudentInfo(appraiseStudent);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateAppraiseStudent appraiseStudent, HttpSession session) {
        UserInfo userInfo=(UserInfo) session.getAttribute(SystemConst.SYSTEM_USER_SESSION);
        if(userInfo!=null){
            if("teacher".equals(userInfo.getRoleKey())){
                appraiseStudent.setTeacherId(userInfo.getId());
            }
        }else {
            return new Result<String>().error(ResponseConstant.NO_LOGIN);
        }
        boolean result=iAppraiseStudentService.createAppraiseStudentInfo(appraiseStudent);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
}
