package com.dlu.upms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.business.dto.AppraiseStudentInfo;
import com.dlu.upms.business.dto.QueryAppraiseStudent;
import com.dlu.upms.business.entity.AppraiseStudent;
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
public interface AppraiseStudentMapper extends BaseMapper<AppraiseStudent> {
    Page<AppraiseStudentInfo> selectAppraiseStudentListForStudent(Page<AppraiseStudentInfo> page, @Param("appraiseStudent") QueryAppraiseStudent appraiseStudent);
    List<AppraiseStudentInfo> selectAppraiseStudentListForStudent(@Param("appraiseStudent") QueryAppraiseStudent appraiseStudent);

}
