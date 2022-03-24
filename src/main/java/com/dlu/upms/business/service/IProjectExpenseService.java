package com.dlu.upms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.CreateProjectExpense;
import com.dlu.upms.business.dto.ProjectExpenseInfo;
import com.dlu.upms.business.dto.QueryProjectExpense;
import com.dlu.upms.business.dto.UpdateProjectExpense;
import com.dlu.upms.business.entity.ProjectExpense;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-03-15
 */
public interface IProjectExpenseService extends IService<ProjectExpense> {

    Page<ProjectExpenseInfo> selectProjectExpenseList(Page<ProjectExpenseInfo> page, QueryProjectExpense project);

    boolean deleteProjectExpenseInfo(String id);

    boolean createProjectExpenseInfo(CreateProjectExpense projectExpense);

    boolean updateProjectExpenseInfo(UpdateProjectExpense projectExpense);
}
