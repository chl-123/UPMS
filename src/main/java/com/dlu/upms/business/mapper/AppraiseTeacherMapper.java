package com.dlu.upms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.ProjectInfo;
import com.dlu.upms.business.dto.QueryProject;
import com.dlu.upms.business.entity.AppraiseTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
public interface AppraiseTeacherMapper extends BaseMapper<AppraiseTeacher> {
    Page<ProjectInfo> selectProjectAppraiseListForTeacher(Page<ProjectInfo> page, @Param("project") QueryProject project);
    List<ProjectInfo> selectProjectAppraiseListForTeacher(  @Param("project") QueryProject project);

}
