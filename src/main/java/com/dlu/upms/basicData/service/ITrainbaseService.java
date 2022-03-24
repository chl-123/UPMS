package com.dlu.upms.basicData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.basicData.dto.CreateTrainbase;
import com.dlu.upms.basicData.dto.QueryTrainbase;
import com.dlu.upms.basicData.dto.TrainbaseInfo;
import com.dlu.upms.basicData.dto.UpdateTrainbase;
import com.dlu.upms.basicData.entity.Trainbase;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
public interface ITrainbaseService extends IService<Trainbase> {

    Page<TrainbaseInfo> selectTrainbaseList(Page<TrainbaseInfo> page, QueryTrainbase trainbase);

    boolean deleteTrainbaseInfo(String id);

    boolean updateTrainbaseInfo(UpdateTrainbase trainbase);

    boolean createTrainbaseInfo(CreateTrainbase trainbase);

    List<TrainbaseInfo> selectList(QueryTrainbase trainbase);
}
