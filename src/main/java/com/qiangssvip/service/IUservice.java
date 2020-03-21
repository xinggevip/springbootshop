package com.qiangssvip.service;

import com.qiangssvip.pojo.User;
import com.qiangssvip.service.vo.ResponseVo;

public interface IUservice {
    /**
     * 注册
     */
    ResponseVo register(User user);

    /**
     * 登录
     */
    ResponseVo login(String username, String password);

}
