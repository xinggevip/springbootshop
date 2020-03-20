package com.qiangssvip.service.impl;

import com.qiangssvip.dao.UserMapper;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class IUserviceImpl implements IUservice {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     *
     * @param user
     */
    @Override
    public void register(User user) {
        int countByUsername = userMapper.countByUsername(user.getUsername());
        int countByEmail = userMapper.countByEmail(user.getEmail());

        if (countByUsername > 0){
            throw new RuntimeException("该username已注册");
        }

        if (countByEmail > 0){
            throw new RuntimeException("该email已注册");
        }

        String digestPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(digestPassword);

        int result = userMapper.insert(user);
        if (result == 0){
            throw new RuntimeException("注册失败");
        }

    }
}
