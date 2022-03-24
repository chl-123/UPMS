package com.dlu.upms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlu.upms.system.dto.QueryUser;
import com.dlu.upms.system.dto.UserInfo;
import com.dlu.upms.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-01-05
 */
public interface UserMapper extends BaseMapper<User> {
    Page<UserInfo> selectUserList(Page<UserInfo> page, @Param("user") QueryUser user);
    List<UserInfo> selectUserList(@Param("user") QueryUser user);

}
