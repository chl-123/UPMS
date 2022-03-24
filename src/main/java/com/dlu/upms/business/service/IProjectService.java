package com.dlu.upms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.CreateProject;
import com.dlu.upms.business.dto.ProjectInfo;
import com.dlu.upms.business.dto.QueryProject;
import com.dlu.upms.business.dto.UpdateProject;
import com.dlu.upms.business.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
public interface IProjectService extends IService<Project> {

    Page<ProjectInfo> selectProjectList(Page<ProjectInfo> page, QueryProject project);

    boolean deleteProjectInfo(String id);

    boolean updateProjectInfo(UpdateProject project);

    boolean createProjectInfo(CreateProject project);

    Page<ProjectInfo> selectProjectAppraiseTeacherList(Page<ProjectInfo> page, QueryProject project);

    Page<ProjectInfo> selectProjectAppraiseStudentList(Page<ProjectInfo> page, QueryProject project);

    Page<ProjectInfo> selectProjectSelectedList(Page<ProjectInfo> page, QueryProject project);

    Page<ProjectInfo> selectStudentScoreList(Page<ProjectInfo> page, QueryProject project);

    Page<ProjectInfo> selectStudentScoreAndAppraiseList(Page<ProjectInfo> page, QueryProject project);
}
