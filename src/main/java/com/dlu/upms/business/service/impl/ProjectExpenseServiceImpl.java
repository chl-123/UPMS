package com.dlu.upms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.business.dto.CreateProjectExpense;
import com.dlu.upms.business.dto.ProjectExpenseInfo;
import com.dlu.upms.business.dto.QueryProjectExpense;
import com.dlu.upms.business.dto.UpdateProjectExpense;
import com.dlu.upms.business.entity.ProjectExpense;
import com.dlu.upms.business.mapper.ProjectExpenseMapper;
import com.dlu.upms.business.service.IProjectExpenseService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-03-15
 */
@Service
public class ProjectExpenseServiceImpl extends ServiceImpl<ProjectExpenseMapper, ProjectExpense> implements IProjectExpenseService {
    @Autowired
    private ProjectExpenseMapper projectExpenseMapper;
    @Override
    public Page<ProjectExpenseInfo> selectProjectExpenseList(Page<ProjectExpenseInfo> page, QueryProjectExpense project) {
        Page<ProjectExpenseInfo> projectExpenseInfoPage=projectExpenseMapper.selectProjectExpenseInfo(page,project);
        return projectExpenseInfoPage;
    }

    @Override
    public boolean deleteProjectExpenseInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("ID不能为空");
        }
        return this.removeById(id);
    }

    @Override
    public boolean createProjectExpenseInfo(CreateProjectExpense projectExpense) {
        if(Utils.isEmpty(projectExpense.getProjectId())){
            throw new BusinessException("项目ID不能为空");
        }
        float total=0;
        total=projectExpense.getTeacherSalary().floatValue()+projectExpense.getSiteCost().floatValue()+projectExpense.getOther().floatValue();
        ProjectExpense projectExpense1=new ProjectExpense();
        BeanUtils.copyProperties(projectExpense,projectExpense1);
        projectExpense1.setTotal(total);
        return this.save(projectExpense1);

    }

    @Override
    public boolean updateProjectExpenseInfo(UpdateProjectExpense projectExpense) {
        if(Utils.isEmpty(projectExpense.getProjectId())){
            throw new BusinessException("项目ID不能为空");
        }
        if(Utils.isEmpty(projectExpense.getId())){
            throw new BusinessException("ID不能为空");
        }
        ProjectExpense projectExpense1=new ProjectExpense();
        BeanUtils.copyProperties(projectExpense,projectExpense1);
        UpdateWrapper<ProjectExpense> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",projectExpense.getId());
        float total=0;
        total=projectExpense.getTeacherSalary().floatValue()+projectExpense.getSiteCost().floatValue()+projectExpense.getOther().floatValue();
        projectExpense1.setTotal(total);
        boolean update = this.update(projectExpense1, updateWrapper);
        return update;
    }
}
