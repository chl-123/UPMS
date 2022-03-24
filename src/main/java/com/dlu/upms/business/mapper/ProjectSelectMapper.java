package com.dlu.upms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlu.upms.business.dto.Charts;
import com.dlu.upms.business.entity.ProjectSelect;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
public interface ProjectSelectMapper extends BaseMapper<ProjectSelect> {
    List<Charts> selectCountList();
}
