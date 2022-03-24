package com.dlu.upms.basicData.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.basicData.dto.CreateTrainbase;
import com.dlu.upms.basicData.dto.QueryTrainbase;
import com.dlu.upms.basicData.dto.TrainbaseInfo;
import com.dlu.upms.basicData.dto.UpdateTrainbase;
import com.dlu.upms.basicData.entity.Trainbase;
import com.dlu.upms.basicData.mapper.TrainbaseMapper;
import com.dlu.upms.basicData.service.ITrainbaseService;
import com.dlu.upms.common.base.BusinessException;
import com.dlu.upms.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-03-05
 */
@Service
public class TrainbaseServiceImpl extends ServiceImpl<TrainbaseMapper, Trainbase> implements ITrainbaseService {
    @Autowired
    private TrainbaseMapper trainbaseMapper;
    @Override
    public Page<TrainbaseInfo> selectTrainbaseList(Page<TrainbaseInfo> page, QueryTrainbase trainbase) {
        Page<TrainbaseInfo> trainbaseInfoPage = trainbaseMapper.selectTrainbaseList(page, trainbase);

        return trainbaseInfoPage;
    }

    @Override
    public boolean deleteTrainbaseInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("ID不能为空");
        }
        return this.removeById(id);
    }

    @Override
    public boolean updateTrainbaseInfo(UpdateTrainbase trainbase) {
        if(Utils.isEmpty(trainbase.getId())){
            throw new BusinessException("ID不能为空");
        }
        Trainbase info=new Trainbase();
        BeanUtils.copyProperties(trainbase,info);
        this.checkInfo(info);
        info.setUpdateTime(new Date());
        UpdateWrapper<Trainbase> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",info.getId());
        boolean result = this.update(info,updateWrapper);
        return result;
    }

    @Override
    public boolean createTrainbaseInfo(CreateTrainbase trainbase) {
        Trainbase info=new Trainbase();
        BeanUtils.copyProperties(trainbase,info);
        this.checkInfo(info);
        info.setUpdateTime(new Date());
        boolean result = this.save(info);
        return result;
    }

    @Override
    public List<TrainbaseInfo> selectList(QueryTrainbase trainbase) {
        List<TrainbaseInfo> trainbaseInfos = trainbaseMapper.selectTrainbaseList(trainbase);
        return trainbaseInfos;
    }

    private void checkInfo(Trainbase trainbase){
        if(Utils.isEmpty(trainbase.getBaseKey())){
            throw new BusinessException("基地编号不能为空");
        }
        if(Utils.isEmpty(trainbase.getBaseName())){
            throw new BusinessException("基地名称不能为空");
        }
        if(Utils.isEmpty(trainbase.getBaseAddress())){
            throw new BusinessException("基地地址不能为空");
        }
        if(Utils.isEmpty(trainbase.getIsInside())){
            throw new BusinessException("是否为校内不能为空");
        }

    }
}
