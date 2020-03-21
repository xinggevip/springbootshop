package com.qiangssvip.service.impl;

import com.qiangssvip.enums.ResponseEnum;
import com.qiangssvip.enums.RoleEnum;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import com.qiangssvip.service.vo.ResponseVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@Transactional // 在测试类添加此注解不会真正的操作数据库，可以避免在测试的时候污染数据
class IUserviceImplTest {

    public String username = "xing77";
    public String password = "123456";
    public String email = "123555@qq.com";

    @Autowired
    private IUservice iUservice;

    @BeforeEach   // 每次执行测试方法前都会执行此方法
    void register() {
        User user = new User(username,password,email, RoleEnum.CUSTOMER.getCode(),new Date(),new Date());
        iUservice.register(user);

    }

    @Test
    void login() {
        ResponseVo responseVo = iUservice.login(username, password);
        Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus()); // 第一个参数未期待的值，第二个参数为实际的值，两个值不相同会停止测试
    }
}