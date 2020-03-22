package com.qiangssvip.service.impl;

import com.qiangssvip.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IProductServiceImplTest {

    @Autowired
    private IProductService iProductService;

    @Test
    void list() {
        iProductService.list(100001,1,10);
    }
}