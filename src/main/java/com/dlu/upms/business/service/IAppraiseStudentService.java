package com.dlu.upms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.AppraiseStudentInfo;
import com.dlu.upms.business.dto.CreateAppraiseStudent;
import com.dlu.upms.business.dto.QueryAppraiseStudent;
import com.dlu.upms.business.dto.UpdateAppraiseStudent;
import com.dlu.upms.business.entity.AppraiseStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
public interface IAppraiseStudentService extends IService<AppraiseStudent> {

    Page<AppraiseStudentInfo> selectAppraiseStudentList(Page<AppraiseStudentInfo> page, QueryAppraiseStudent appraiseStudent);

    boolean deleteAppraiseStudentInfo(String id);

    boolean updateAppraiseStudentInfo(UpdateAppraiseStudent appraiseStudent);

    boolean createAppraiseStudentInfo(CreateAppraiseStudent appraiseStudent);
}
