package com.dlu.upms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.business.dto.*;
import com.dlu.upms.business.entity.AppraiseTeacher;
import com.dlu.upms.business.mapper.AppraiseTeacherMapper;
import com.dlu.upms.business.service.IAppraiseTeacherService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
@Service
public class AppraiseTeacherServiceImpl extends ServiceImpl<AppraiseTeacherMapper, AppraiseTeacher> implements IAppraiseTeacherService {
    @Autowired
    private AppraiseTeacherMapper appraiseTeacherMapper;
    @Override
    public boolean createAppraiseTeacherInfo(CreateAppraiseTeacher appraiseTeacher) {
        AppraiseTeacher info=new AppraiseTeacher();
        BeanUtils.copyProperties(appraiseTeacher,info);
        if(Utils.isEmpty(appraiseTeacher.getStudentId())){
            throw new BusinessException("学生ID不能为空");
        }
        this.checkInfo(info);
        info.setUpdateTime(new Date());
        boolean result = this.save(info);
        return result;
    }

    @Override
    public boolean updateAppraiseTeacherInfo(UpdateAppraiseTeacher appraiseTeacher) {
        if(Utils.isEmpty(appraiseTeacher.getId())){
            throw new BusinessException("ID不能为空");
        }
        AppraiseTeacher info=new AppraiseTeacher();
        BeanUtils.copyProperties(appraiseTeacher,info);
        this.checkInfo(info);
        info.setUpdateTime(new Date());
        UpdateWrapper<AppraiseTeacher> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",info.getId());
        boolean result = this.update(info,updateWrapper);
        return result;
    }

    @Override
    public boolean deleteAppraiseTeacherInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("ID不能为空");
        }
        return this.removeById(id);
    }

    @Override
    public Page<ProjectInfo> selectProjectAppraiseListForTeacher(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> appraiseTeacherInfoPage = appraiseTeacherMapper.selectProjectAppraiseListForTeacher(page, project);
        return appraiseTeacherInfoPage;
    }
    private void checkInfo(AppraiseTeacher appraiseTeacher){

        if(Utils.isEmpty(appraiseTeacher.getTeacherId())){
            throw new BusinessException("老师ID不能为空");
        }
        if(Utils.isEmpty(appraiseTeacher.getProjectId())){
            throw new BusinessException("项目ID不能为空");
        }
        if(
                Utils.isEmpty(appraiseTeacher.getMethodUseful())||Utils.isEmpty(appraiseTeacher.getTeachingLevel())||
                        Utils.isEmpty(appraiseTeacher.getOpinions())||Utils.isEmpty(appraiseTeacher.getProblem())||
                        Utils.isEmpty(appraiseTeacher.getRewards())
        ) {
            throw new BusinessException("评价信息不能为空");
        }
    }
}
