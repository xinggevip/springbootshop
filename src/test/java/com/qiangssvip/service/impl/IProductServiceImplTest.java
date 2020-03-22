package com.qiangssvip.service.impl;

import com.github.pagehelper.PageInfo;
import com.qiangssvip.service.IProductService;
import com.qiangssvip.service.vo.ResponseVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class IProductServiceImplTest {

    @Autowired
    private IProductService iProductService;

    @Test
    void list() {
        ResponseVo<PageInfo> list = iProductService.list(null, 1, 10);
        PageInfo data = list.getData();
        System.out.println(data);

    }
}