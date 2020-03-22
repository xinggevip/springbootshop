package com.qiangssvip.controller;

import com.qiangssvip.consts.MallConst;
import com.qiangssvip.form.UserForm;
import com.qiangssvip.form.UserLoginForm;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUservice iUservice;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm){

        User user = new User();
        BeanUtils.copyProperties(userForm,user);  // 拷贝对象的属性

        log.info("username={}",userForm);
        return iUservice.register(user);
    }

    @PostMapping("/login")
    public ResponseVo login(@Valid @RequestBody UserLoginForm user,
                            HttpSession session) {

        ResponseVo<User> login = iUservice.login(user.getUsername(), user.getPassword());

        session.setAttribute(MallConst.CURRENT_USER,login.getData());
        log.info("/login ssionId = {}",session.getId());

        return login;
    }

    @GetMapping("/getUser")
    public ResponseVo<User> userInfo(HttpSession session) {
        log.info("/getUser ssionId = {}",session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.successs(user);
    }

    // TODO 判断登录状态，拦截器
    @PostMapping("/loginOut")
    public ResponseVo loginOut(HttpSession session){

        log.info("/loginOut ssionId = {}",session.getId());
        // 删除session
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }




}
