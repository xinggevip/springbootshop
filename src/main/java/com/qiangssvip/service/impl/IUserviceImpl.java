package com.qiangssvip.service.impl;

import com.qiangssvip.dao.UserMapper;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import com.qiangssvip.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.qiangssvip.enums.ResponseEnum.*;

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
    public ResponseVo register(User user) {
//        err();
        int countByUsername = userMapper.countByUsername(user.getUsername());
        int countByEmail = userMapper.countByEmail(user.getEmail());

        if (countByUsername > 0){
//            throw new RuntimeException("该username已注册");
            return ResponseVo.error(USER_EXIST);
        }

        if (countByEmail > 0){
//            throw new RuntimeException("该email已注册");
            return ResponseVo.error(EMAILE_EXIST);
        }

        String digestPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(digestPassword);

        user.setRole(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        int result = userMapper.insert(user);
        if (result == 0){
//            throw new RuntimeException("注册失败");
            return ResponseVo.error(REGISTER_FAILED);
        }



        return ResponseVo.success();

    }

    @Override
    public ResponseVo login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            if (user.getPassword().equals(digestPassword)) {
                return ResponseVo.successs(user);
            }
            return ResponseVo.error(LOGIN_ERROR);
        }
        return ResponseVo.error(USER_NOFIND);
    }

    public void err(){
        throw new RuntimeException("未知的错误");
    }

}
