package com.dlu.upms.business.service;

import com.dlu.upms.business.dto.Charts;
import com.dlu.upms.business.dto.CreateProjectSelect;
import com.dlu.upms.business.dto.UpdateProjectSelect;
import com.dlu.upms.business.entity.ProjectSelect;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
public interface IProjectSelectService extends IService<ProjectSelect> {

    boolean deleteProjectSelectInfo(CreateProjectSelect projectSelect);

    boolean createProjectSelectInfo(CreateProjectSelect projectSelect);

    boolean updateProjectSelectInfo(UpdateProjectSelect projectSelect);

    List<Charts> selectCount(ProjectSelect projectSelect);

    List<Charts> selectScore(ProjectSelect projectSelect);

    List<Charts> selectScoreCount(ProjectSelect projectSelect);
}
