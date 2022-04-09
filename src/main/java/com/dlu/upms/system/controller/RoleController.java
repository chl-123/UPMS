package com.dlu.upms.system.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.xmSelect;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.Result;
import com.dlu.upms.system.dto.CreateRole;
import com.dlu.upms.system.dto.QueryRole;
import com.dlu.upms.system.dto.RoleInfo;
import com.dlu.upms.system.dto.UpdateRole;
import com.dlu.upms.system.service.IRoleService;
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
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @GetMapping("/page/list")
    public PageResult<RoleInfo> pageList(QueryRole role, Page<RoleInfo> page,HttpSession session) {
        Page<RoleInfo> pageLog = iRoleService.selectRolePageList(page, role);
        PageResult<RoleInfo> pageResult = new PageResult<RoleInfo>(pageLog.getTotal(), pageLog.getRecords());
        return pageResult;
    }
    @GetMapping("/list")
    public Result<List<xmSelect>> list(QueryRole role) {
        List<RoleInfo> ListInfo = iRoleService.selectRoleList(role);
        List<xmSelect> xmSelects=new ArrayList<>();
        if(!CollectionUtils.isEmpty(ListInfo)){
            for(RoleInfo collegeInfo:ListInfo){
                if(collegeInfo!=null){
                    xmSelect xmSelect=new xmSelect();
                    xmSelect.setName(collegeInfo.getRoleName());
                    xmSelect.setValue(collegeInfo.getId());
                    xmSelects.add(xmSelect);
                }
            }
        }

        return new Result<List<xmSelect>>().success().put(xmSelects);
    }
    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id, HttpSession session) {

        boolean result=iRoleService.deleteRoleInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateRole role) {
        boolean result=iRoleService.updateRoleInfo(role);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateRole role) {
        boolean result=iRoleService.createRoleInfo(role);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
}
