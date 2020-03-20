package com.qiangssvip.service.impl;

import com.qiangssvip.enums.RoleEnum;
import com.qiangssvip.pojo.User;
import com.qiangssvip.service.IUservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 在测试类添加此注解不会真正的操作数据库，可以避免在测试的时候污染数据
class IUserviceImplTest {

    @Autowired
    private IUservice iUservice;

    @Test
    void register() {
        User user = new User("xioaming123","123456","151184426233@qq.com", RoleEnum.CUSTOMER.getCode(),new Date(),new Date());
        iUservice.register(user);

    }
}