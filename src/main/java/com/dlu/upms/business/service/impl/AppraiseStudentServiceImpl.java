package com.dlu.upms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.business.dto.AppraiseStudentInfo;
import com.dlu.upms.business.dto.CreateAppraiseStudent;
import com.dlu.upms.business.dto.QueryAppraiseStudent;
import com.dlu.upms.business.dto.UpdateAppraiseStudent;
import com.dlu.upms.business.entity.AppraiseStudent;
import com.dlu.upms.business.mapper.AppraiseStudentMapper;
import com.dlu.upms.business.service.IAppraiseStudentService;
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
public class AppraiseStudentServiceImpl extends ServiceImpl<AppraiseStudentMapper, AppraiseStudent> implements IAppraiseStudentService {
    @Autowired
    private AppraiseStudentMapper  appraiseStudentMapper;
    @Override
    public Page<AppraiseStudentInfo> selectAppraiseStudentList(Page<AppraiseStudentInfo> page, QueryAppraiseStudent appraiseStudent) {
        Page<AppraiseStudentInfo> appraiseStudentInfoPage = appraiseStudentMapper.selectAppraiseStudentListForStudent(page, appraiseStudent);
        return appraiseStudentInfoPage;
    }

    @Override
    public boolean deleteAppraiseStudentInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("ID不能为空");
        }
        return this.removeById(id);
    }

    @Override
    public boolean updateAppraiseStudentInfo(UpdateAppraiseStudent appraiseStudent) {
        if(Utils.isEmpty(appraiseStudent.getId())){
            throw new BusinessException("ID不能为空");
        }
        AppraiseStudent info=new AppraiseStudent();
        BeanUtils.copyProperties(appraiseStudent,info);
        this.checkInfo(info);
        info.setUpdateTime(new Date());
        UpdateWrapper<AppraiseStudent> appraiseStudentUpdateWrapper=new UpdateWrapper<>();
        appraiseStudentUpdateWrapper.eq("id",info.getId());
        boolean result = this.update(info,appraiseStudentUpdateWrapper);
        return result;
    }

    @Override
    public boolean createAppraiseStudentInfo(CreateAppraiseStudent appraiseStudent) {

        AppraiseStudent info=new AppraiseStudent();
        BeanUtils.copyProperties(appraiseStudent,info);
        this.checkInfo(info);
        if(Utils.isEmpty(appraiseStudent.getTeacherId())){
            throw new BusinessException("老师ID不能为空");
        }
        info.setUpdateTime(new Date());
        boolean result = this.save(info);
        return result;
    }
    private void checkInfo(AppraiseStudent appraiseStudent){
        if(Utils.isEmpty(appraiseStudent.getStudentId())){
            throw new BusinessException("学生ID不能为空");
        }

        if(Utils.isEmpty(appraiseStudent.getProjectId())){
            throw new BusinessException("项目ID不能为空");
        }
        if(
                Utils.isEmpty(appraiseStudent.getJobStatus())||Utils.isEmpty(appraiseStudent.getLearningStatus())||
                        Utils.isEmpty(appraiseStudent.getOpinions())||Utils.isEmpty(appraiseStudent.getProblem())
        ) {
            throw new BusinessException("评价信息不能为空");
        }
    }
}
