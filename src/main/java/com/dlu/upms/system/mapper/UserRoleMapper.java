package com.dlu.upms.system.mapper;

import com.dlu.upms.system.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-01-06
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户id，查询多个角色标识
     */
    Set<String> listUserRoleByUserId(@Param("userId") String userId);
}
