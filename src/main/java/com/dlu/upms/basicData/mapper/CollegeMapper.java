package com.dlu.upms.basicData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.CollegeInfo;
import com.dlu.upms.basicData.dto.QueryCollege;
import com.dlu.upms.basicData.entity.College;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-02-09
 */
public interface CollegeMapper extends BaseMapper<College> {

    Page<CollegeInfo> selectCollegeList(Page<CollegeInfo> page,@Param("college") QueryCollege college);
    List<CollegeInfo> selectCollegeList(@Param("college")QueryCollege college);

}
