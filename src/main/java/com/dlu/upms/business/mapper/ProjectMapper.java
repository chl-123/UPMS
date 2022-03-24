package com.dlu.upms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.ProjectInfo;
import com.dlu.upms.business.dto.QueryProject;
import com.dlu.upms.business.entity.Project;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
public interface ProjectMapper extends BaseMapper<Project> {
    Page<ProjectInfo> selectProjectListForStudent(Page<ProjectInfo> page, @Param("project") QueryProject project);
    Page<ProjectInfo> selectProjectListForTeacher(Page<ProjectInfo> page, @Param("project") QueryProject project);
    Page<ProjectInfo> selectProjectAppraiseListForStudent(Page<ProjectInfo> page, @Param("project") QueryProject project);
    Page<ProjectInfo> selectProjectAppraiseListForTeacher(Page<ProjectInfo> page, @Param("project") QueryProject project);
    Page<ProjectInfo> selectProjectSelectedForStudent(Page<ProjectInfo> page, @Param("project") QueryProject project);

    Page<ProjectInfo> selectStudentScoreForTeacher(Page<ProjectInfo> page, @Param("project")QueryProject project);
    Page<ProjectInfo> selectAppraiseScoreListForStudent(Page<ProjectInfo> page,@Param("project") QueryProject project);
}
