package com.dlu.upms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.ProjectExpenseInfo;
import com.dlu.upms.business.dto.QueryProjectExpense;
import com.dlu.upms.business.entity.ProjectExpense;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-03-15
 */
public interface ProjectExpenseMapper extends BaseMapper<ProjectExpense> {

    Page<ProjectExpenseInfo> selectProjectExpenseInfo(Page<ProjectExpenseInfo> page, QueryProjectExpense project);
}
