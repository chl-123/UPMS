package com.dlu.upms.basicData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.QueryTrainbase;
import com.dlu.upms.basicData.dto.TrainbaseInfo;
import com.dlu.upms.basicData.entity.Trainbase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
public interface TrainbaseMapper extends BaseMapper<Trainbase> {
    Page<TrainbaseInfo> selectTrainbaseList(Page<TrainbaseInfo> page, @Param("trainbase") QueryTrainbase trainbase);
    List<TrainbaseInfo> selectTrainbaseList(@Param("trainbase") QueryTrainbase trainbase);
}
