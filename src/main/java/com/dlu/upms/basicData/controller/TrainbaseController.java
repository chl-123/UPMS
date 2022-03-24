package com.dlu.upms.basicData.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dlu.upms.basicData.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.service.ITrainbaseService;
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
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/basicData/trainbase")
public class TrainbaseController {
    @Autowired
    private ITrainbaseService iTrainbaseService;

    @GetMapping("/page/list")
    public PageResult<TrainbaseInfo> pageList(QueryTrainbase trainbase, Page<TrainbaseInfo> page) {
        Page<TrainbaseInfo> pageInfo = iTrainbaseService.selectTrainbaseList(page, trainbase);
        PageResult<TrainbaseInfo> pageResult = new PageResult<TrainbaseInfo>(pageInfo.getTotal(), pageInfo.getRecords());
        return pageResult;
    }
    @GetMapping("/list")
    public Result<List<xmSelect>> list(QueryTrainbase trainbase) {
        List<TrainbaseInfo> ListInfo = iTrainbaseService.selectList(trainbase);
        List<xmSelect> xmSelects=new ArrayList<>();
        if(!CollectionUtils.isEmpty(ListInfo)){
            for(TrainbaseInfo collegeInfo:ListInfo){
                if(collegeInfo!=null){
                    xmSelect xmSelect=new xmSelect();
                    xmSelect.setName(collegeInfo.getBaseName());
                    xmSelect.setValue(collegeInfo.getId());
                    xmSelects.add(xmSelect);
                }
            }
        }

        return new Result<List<xmSelect>>().success().put(xmSelects);
    }
    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable("id")String id) {

        boolean result=iTrainbaseService.deleteTrainbaseInfo(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody UpdateTrainbase trainbase) {
        System.out.println(trainbase.toString());
        boolean result=iTrainbaseService.updateTrainbaseInfo(trainbase);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
    @PostMapping("/create")
    public Result<?> create(@RequestBody CreateTrainbase trainbase) {
        boolean result=iTrainbaseService.createTrainbaseInfo(trainbase);
        if (result) {
            return new Result<>().success("更新成功");

        }else {
            return new Result<>().error("更新失败，请重试");
        }
    }
}
