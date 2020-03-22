package com.qiangssvip.service.impl;

import com.github.pagehelper.PageInfo;
import com.qiangssvip.service.IProductService;
import com.qiangssvip.service.vo.ProductDetailVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class IProductServiceImplTest {

    @Autowired
    private IProductService iProductService;

    @Test
    void list() {
        ResponseVo<PageInfo> list = iProductService.list(null, 1, 10);
        PageInfo data = list.getData();
        System.out.println(data);

    }

    @Test
    void detail() {
        ResponseVo<ProductDetailVo> detail = iProductService.detail(26);
        log.info("data={}",detail);
    }
}