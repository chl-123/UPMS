package com.dlu.upms.system.controller;

import com.dlu.upms.common.base.ResponseConstant;
import com.dlu.upms.common.base.Result;
import com.dlu.upms.common.consts.SystemConst;
import com.dlu.upms.system.dto.Login;
import com.dlu.upms.system.dto.QueryUser;
import com.dlu.upms.system.dto.UserInfo;
import com.dlu.upms.system.service.IUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private IUserService iUserService;

    @PostMapping("doLogin")
    @ResponseBody
    public Result<?> doLogin(Login login, HttpServletRequest request) throws Exception {


        Object tokenCredentials = new SimpleHash("md5", login.getUserPassword(), login.getUserAccount(), 6).toHex();
        QueryUser queryUser = new QueryUser();
        queryUser.setUserAccount(login.getUserAccount());
        List<UserInfo> userInfos = iUserService.selectList(queryUser);
        if (userInfos.size() == 0) {
            return new Result<String>().error(ResponseConstant.INVALID_ACCOUNT);
        }
        Object password = userInfos.get(0).getUserPassword();
        if (!password.equals(tokenCredentials)) {
            return new Result<String>().error(ResponseConstant.INVALID_PASSWORD);
        }
        request.getSession().setAttribute(SystemConst.SYSTEM_USER_SESSION, userInfos.get(0));
        // 保存登录日志
        return new Result<UserInfo>().success(ResponseConstant.LOGIN_SUCCESS).put(userInfos.get(0));

    }
}
