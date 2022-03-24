package com.dlu.upms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.business.dto.Charts;
import com.dlu.upms.business.dto.CreateProjectSelect;
import com.dlu.upms.business.dto.UpdateProjectSelect;
import com.dlu.upms.business.entity.Project;
import com.dlu.upms.business.entity.ProjectSelect;
import com.dlu.upms.business.mapper.ProjectSelectMapper;
import com.dlu.upms.business.service.IProjectSelectService;
import com.dlu.upms.business.service.IProjectService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
@Service
public class ProjectSelectServiceImpl extends ServiceImpl<ProjectSelectMapper, ProjectSelect> implements IProjectSelectService {
    @Lazy
    @Autowired
    private IProjectService iProjectService;
    @Autowired
    private ProjectSelectMapper projectSelectMapper;
    @Override
    public boolean deleteProjectSelectInfo(CreateProjectSelect projectSelect) {
        if(Utils.isEmpty(projectSelect.getProjectSelectId())){
            throw new BusinessException("id不能为空");
        }
        Project project = iProjectService.getById(projectSelect.getProjectId());
        if(project!=null){
            if(project.getSelectNumMax()!=null&&project.getSelectNum()!=null){
                if(project.getSelectNum()>0){
                    UpdateWrapper<Project> updateWrapper=new UpdateWrapper<>();
                    updateWrapper.eq("id",projectSelect.getProjectId());
                    updateWrapper.set("select_num",project.getSelectNum()-1);
                    boolean result1 = iProjectService.update(updateWrapper);
                    if(!result1){
                        throw new BusinessException("退选失败");
                    }

                }
            }
        }
        boolean result = this.removeById(projectSelect.getProjectSelectId());
        return result;
    }

    @Override
    public boolean createProjectSelectInfo(CreateProjectSelect projectSelect) {
        if(Utils.isEmpty(projectSelect.getProjectId())){
            throw new BusinessException("项目ID不能为空");
        }
        if(Utils.isEmpty(projectSelect.getStudentId())){
            throw new BusinessException("学生ID不能为空");
        }
        if(Utils.isEmpty(projectSelect.getTeacherId())){
            throw new BusinessException("老师ID不能为空");
        }

        QueryWrapper<ProjectSelect> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("project_id",projectSelect.getProjectId()).and(e->e.eq("student_id",projectSelect.getStudentId()));
        List<ProjectSelect> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException("不能重复选择");
        }
        Project project = iProjectService.getById(projectSelect.getProjectId());
        if(project!=null){
            if(project.getSelectNumMax()!=null&&project.getSelectNum()!=null){
                if(project.getSelectNumMax()==project.getSelectNum()){
                    throw new BusinessException("该项目人数已达到上限，请选择其他项目");
                }else if(project.getSelectNum()<project.getSelectNumMax()){
                    ProjectSelect projectSelectInfo=new ProjectSelect();
                    BeanUtils.copyProperties(projectSelect,projectSelectInfo);
                    projectSelectInfo.setUpdateTime(new Date());
                    boolean result = this.save(projectSelectInfo);
                    if(!result){
                        throw new BusinessException("选择失败");
                    }
                    UpdateWrapper<Project> updateWrapper=new UpdateWrapper<>();
                    updateWrapper.eq("id",projectSelect.getProjectId());
                    updateWrapper.set("select_num",project.getSelectNum()+1);
                    boolean result1 = iProjectService.update(updateWrapper);
                    if(!result1){
                        throw new BusinessException("选择失败");
                    }

                }
            }
        }

        return true;
    }

    @Override
    public boolean updateProjectSelectInfo(UpdateProjectSelect projectSelect) {
        if(Utils.isEmpty(projectSelect.getProjectId())){
            throw new BusinessException("项目ID不能为空");
        }
        if(Utils.isEmpty(projectSelect.getStudentId())){
            throw new BusinessException("学生ID不能为空");
        }
        if(Utils.isEmpty(projectSelect.getTeacherId())){
            throw new BusinessException("老师ID不能为空");
        }
        if(Utils.isEmpty(projectSelect.getId())){
            throw new BusinessException("id不能为空");
        }
        UpdateWrapper<ProjectSelect> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",projectSelect.getId());
        ProjectSelect projectSelect1=new ProjectSelect();

        BeanUtils.copyProperties(projectSelect,projectSelect1);
        boolean update = this.update(projectSelect1, updateWrapper);
        return update;


    }

    @Override
    public List<Charts> selectCount() {
        List<Charts> charts = projectSelectMapper.selectCountList();

        return charts;
    }


}
