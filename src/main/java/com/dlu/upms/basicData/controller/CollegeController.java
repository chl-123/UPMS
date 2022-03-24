package com.dlu.upms.basicData.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.*;
import com.dlu.upms.basicData.service.ICollegeService;
import com.dlu.upms.common.base.PageResult;
import com.dlu.upms.common.base.Result;
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
 * @since 2022-02-09
 */
@RestController
@RequestMapping("/basicData/college")
public class CollegeController {
    @Autowired
    private ICollegeService iCollegeService;

    @GetMapping("/page/list")
    public PageResult<CollegeInfo> pageList(QueryCollege college, Page<CollegeInfo> page) {
        Page<CollegeInfo> pageInfo = iCollegeService.selectCollegePageList(page, college);
        PageResult<CollegeInfo> pageResult = new PageResult<CollegeInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/list")
    public Result<List<xmSelect>> list(QueryCollege college) {
        List<CollegeInfo> ListInfo = iCollegeService.selectCollegeList(college);
        List<xmSelect> xmSelects=new ArrayList<>();
        if(!CollectionUtils.isEmpty(ListInfo)){
            for(CollegeInfo collegeInfo:ListInfo){
                if(collegeInfo!=null){
                    xmSelect xmSelect=new xmSelect();
                    xmSelect.setName(collegeInfo.getCollegeName());
                    xmSelect.setValue(collegeInfo.getId());
                    xmSelects.add(xmSelect);
                }
            }
        }

        return new Result<List<xmSelect>>().success().put(xmSelects);
    }

    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id) {

        boolean result=iCollegeService.deleteCollegeInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateCollege college) {
        boolean result=iCollegeService.updateCollegeInfo(college);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateCollege college) {
        boolean result=iCollegeService.createCollegeInfo(college);
        if (result) {
            return new Result<>().success("创建成功");

        }else {
            return new Result<>().error("创建失败，请重试");
        }
    }

}
