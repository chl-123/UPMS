package com.dlu.upms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.basicData.dto.CollegeInfo;
import com.dlu.upms.basicData.dto.QueryCollege;
import com.dlu.upms.basicData.dto.xmSelect;
import com.dlu.upms.basicData.entity.College;
import com.dlu.upms.basicData.service.ICollegeService;
import com.dlu.upms.business.dto.CreateProject;
import com.dlu.upms.business.dto.ProjectInfo;
import com.dlu.upms.business.dto.QueryProject;
import com.dlu.upms.business.dto.UpdateProject;
import com.dlu.upms.business.entity.Project;
import com.dlu.upms.business.entity.ProjectSelect;
import com.dlu.upms.business.mapper.ProjectMapper;
import com.dlu.upms.business.service.IProjectSelectService;
import com.dlu.upms.business.service.IProjectService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    IProjectSelectService iProjectSelectService;
    @Autowired
    ICollegeService iCollegeService;
    @Override
    public Page<ProjectInfo> selectProjectList(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> projectInfoPage=null;
        if(Utils.isEmpty(project.getStudentId())){
            projectInfoPage = projectMapper.selectProjectListForTeacher(page, project);

        }else {
            projectInfoPage = projectMapper.selectProjectListForStudent(page, project);

        }
        List<ProjectInfo> projectInfoList = projectInfoPage.getRecords();
        List<CollegeInfo> collegeInfos = iCollegeService.selectCollegeList(new QueryCollege());
        for (ProjectInfo projectInfo:projectInfoList) {
            if(projectInfo!=null){
                String collegeIds=projectInfo.getCollegeId();
                if (!Utils.isEmpty(collegeIds)) {

                    List<xmSelect> xmSelectList=new ArrayList<xmSelect>();
                    String[] collegeIdList = collegeIds.split(",");
                    List<String> idList=new ArrayList<>();
                    if(collegeIdList.length!=0){
                        Collections.addAll(idList, collegeIdList);
                    }
                    List<College> collegeList = iCollegeService.listByIds(idList);
                    for(CollegeInfo collegeInfo:collegeInfos){
                        xmSelect xmSelect=new xmSelect();
                        if(collegeInfo!=null){
                            if(!Utils.isEmpty(collegeInfo.getCollegeName())&&!Utils.isEmpty(collegeInfo.getCollegeKey())){
                                xmSelect.setName(collegeInfo.getCollegeName());
                                xmSelect.setValue(collegeInfo.getId());
                                for(College college:collegeList){
                                        if(college!=null){
                                            if(!Utils.isEmpty(college.getCollegeName())&&!Utils.isEmpty(college.getId())){
                                                if(collegeInfo.getId().equals(college.getId())){
                                                    xmSelect.setSelected(true);
                                                }
                                            }

                                        }
                                }
                                xmSelectList.add(xmSelect);
                            }
                        }



                    }


                    projectInfo.setCollegeList(this.disableSerializerFeature(xmSelectList));
                }
            }

        }

        return projectInfoPage;
    }

    private List<xmSelect> disableSerializerFeature(List<xmSelect> list) {
        List<xmSelect> listNew = new ArrayList<xmSelect>();
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(i->{
                xmSelect target=new xmSelect();
                BeanUtils.copyProperties(i,target);
                listNew.add(target);
            });
        }

        return listNew;
    }

    @Override
    public boolean deleteProjectInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("id不能为空");
        }
        QueryWrapper<ProjectSelect> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("project_id",id);
        List<ProjectSelect> list = iProjectSelectService.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            QueryWrapper<ProjectSelect> queryWrapperDelete=new QueryWrapper<>();
            queryWrapperDelete.eq("project_id",id);
            boolean result = iProjectSelectService.remove(queryWrapperDelete);
            if(!result){
                throw new BusinessException("删除失败");
            }
        }
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateProjectInfo(UpdateProject project) {
        if(Utils.isEmpty(project.getId())){
            throw new BusinessException("id不能为空");
        }
        if(Utils.isEmpty(project.getProjectKey())){
            throw new BusinessException("角色标记不能为空");
        }
        if(Utils.isEmpty(project.getProjectName())){
            throw new BusinessException("角色名称不能为空");
        }
        if(Utils.isEmpty(project.getCollegeId())){
            throw new BusinessException("学院ID不能为空");
        }
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("project_key",project.getProjectKey()).and(e->e.ne("id",project.getId()));
        List<Project> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException("项目编号在系统中已存在");
        }
        Project updateProject=new Project();
        BeanUtils.copyProperties(project,updateProject);
        System.out.println(updateProject.toString());
        UpdateWrapper<Project> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",project.getId());
        boolean result = this.update(updateProject,updateWrapper);
        return result;
    }

    @Override
    public boolean createProjectInfo(CreateProject project) {
        if(Utils.isEmpty(project.getProjectKey())){
            throw new BusinessException("角色标记不能为空");
        }
        if(Utils.isEmpty(project.getProjectName())){
            throw new BusinessException("角色名称不能为空");
        }
        if(Utils.isEmpty(project.getTeacherId())){
            throw new BusinessException("老师ID不能为空");
        }
        if(Utils.isEmpty(project.getCollegeId())){
            throw new BusinessException("学院ID不能为空");
        }
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("project_key",project.getProjectKey());
        List<Project> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException("项目编号在系统中已存在");
        }
        Project projectInfo=new Project();
        BeanUtils.copyProperties(project,projectInfo);
        projectInfo.setUpdateTime(new Date());
        boolean result = this.save(projectInfo);
        return result;
    }

    @Override
    public Page<ProjectInfo> selectProjectAppraiseTeacherList(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> projectInfoPage = projectMapper.selectProjectAppraiseListForTeacher(page, project);
        return projectInfoPage;
    }

    @Override
    public Page<ProjectInfo> selectProjectAppraiseStudentList(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> projectInfoPage = projectMapper.selectProjectAppraiseListForStudent(page, project);
        return projectInfoPage;
    }

    @Override
    public Page<ProjectInfo> selectProjectSelectedList(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> projectInfoPage = projectMapper.selectProjectSelectedForStudent(page, project);
        return projectInfoPage;
    }

    @Override
    public Page<ProjectInfo> selectStudentScoreList(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> projectInfoPage = projectMapper.selectStudentScoreForTeacher(page, project);
        return projectInfoPage;
    }

    @Override
    public Page<ProjectInfo> selectStudentScoreAndAppraiseList(Page<ProjectInfo> page, QueryProject project) {
        Page<ProjectInfo> projectInfoPage = projectMapper.selectAppraiseScoreListForStudent(page, project);
        return projectInfoPage;
    }
}
