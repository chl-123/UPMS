package com.dlu.upms.basicData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.CollegeInfo;
import com.dlu.upms.basicData.dto.CreateCollege;
import com.dlu.upms.basicData.dto.QueryCollege;
import com.dlu.upms.basicData.dto.UpdateCollege;
import com.dlu.upms.basicData.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-02-09
 */
public interface ICollegeService extends IService<College> {

    Page<CollegeInfo> selectCollegePageList(Page<CollegeInfo> page, QueryCollege college);

    boolean deleteCollegeInfo(String id);

    boolean updateCollegeInfo(UpdateCollege college);

    boolean createCollegeInfo(CreateCollege college);

    List<CollegeInfo> selectCollegeList(QueryCollege college);
}
