package com.dlu.upms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.*;
import com.dlu.upms.business.entity.AppraiseTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
public interface IAppraiseTeacherService extends IService<AppraiseTeacher> {

    boolean createAppraiseTeacherInfo(CreateAppraiseTeacher appraiseTeacher);

    boolean updateAppraiseTeacherInfo(UpdateAppraiseTeacher appraiseTeacher);

    boolean deleteAppraiseTeacherInfo(String id);


    Page<ProjectInfo> selectProjectAppraiseListForTeacher(Page<ProjectInfo> page, QueryProject project);
}
