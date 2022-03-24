package com.dlu.upms.basicData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlu.upms.basicData.dto.CollegeInfo;
import com.dlu.upms.basicData.dto.CreateCollege;
import com.dlu.upms.basicData.dto.QueryCollege;
import com.dlu.upms.basicData.dto.UpdateCollege;
import com.dlu.upms.basicData.entity.College;
import com.dlu.upms.basicData.mapper.CollegeMapper;
import com.dlu.upms.basicData.service.ICollegeService;
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
 * @since 2022-02-09
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements ICollegeService {

    @Autowired
    CollegeMapper collegeMapper;
    @Override
    public Page<CollegeInfo> selectCollegePageList(Page<CollegeInfo> page, QueryCollege college) {
        Page<CollegeInfo>   collegeInfoPage = collegeMapper.selectCollegeList(page, college);
        return collegeInfoPage;
    }
    @Override
    public List<CollegeInfo> selectCollegeList(QueryCollege college) {
        List<CollegeInfo> collegeInfos = collegeMapper.selectCollegeList(college);
        return collegeInfos;
    }



    @Override
    public boolean deleteCollegeInfo(String id) {
        if(Utils.isEmpty(id)){
            throw new BusinessException("id不能为空");
        }
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateCollegeInfo(UpdateCollege college) {
        if(Utils.isEmpty(college.getId())){
            throw new BusinessException("id不能为空");
        }
        if(Utils.isEmpty(college.getCollegeKey())){
            throw new BusinessException("角色标记不能为空");
        }
        if(Utils.isEmpty(college.getCollegeName())){
            throw new BusinessException("角色名称不能为空");
        }
        QueryWrapper<College> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("college_key",college.getCollegeKey());
        List<College> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException("项目编号在系统中已存在");
        }
        College updateCollege=new College();
        BeanUtils.copyProperties(college,updateCollege);
        UpdateWrapper<College> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",college.getId());
        boolean result = this.update(updateCollege,updateWrapper);
        return result;
    }

    @Override
    public boolean createCollegeInfo(CreateCollege college) {
        if(Utils.isEmpty(college.getCollegeKey())){
            throw new BusinessException("角色标记不能为空");
        }
        if(Utils.isEmpty(college.getCollegeName())){
            throw new BusinessException("角色名称不能为空");
        }
        QueryWrapper<College> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("college_key",college.getCollegeKey());
        List<College> list = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException("项目编号在系统中已存在");
        }
        College collegeInfo=new College();
        BeanUtils.copyProperties(college,collegeInfo);
        collegeInfo.setUpdateTime(new Date());
        boolean result = this.save(collegeInfo);
        return result;
    }


}
