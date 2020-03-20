package com.qiangssvip.controller;

import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.qiangssvip.enums.ResponseEnum.NEED_LOGIN;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUservice iUservice;

    @RequestMapping("/register")
    public ResponseVo register(@RequestBody User user){
        log.info("username={}",user);
//        return ResponseVo.success();
//        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        return ResponseVo.error(NEED_LOGIN);
    }

}
