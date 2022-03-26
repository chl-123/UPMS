package com.dlu.upms.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dlu.upms.system.dto.CreateUser;
import com.dlu.upms.system.dto.QueryUser;
import com.dlu.upms.system.dto.UpdateUser;
import com.dlu.upms.system.dto.UserInfo;
import com.dlu.upms.system.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-01-05
 */
public interface IUserService extends IService<User> {

    Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUser user);

    boolean deleteUserInfo(String id, String userRoleId);

    boolean updateUserInfo(UpdateUser user);

    boolean createUserInfo(CreateUser user);

    List<UserInfo> selectList(QueryUser user);

    public User queryUserByAccount(String userAccount);

    boolean batchCreateUser(String flag, MultipartFile file) throws Exception;
}
