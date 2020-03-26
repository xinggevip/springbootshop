package com.qiangssvip.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qiangssvip.service.IOrderService;
import com.qiangssvip.service.vo.OrderVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class IOrderServiceImplTest {

    @Autowired
    private IOrderService orderService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(12, 11);
        log.info("list = {}",gson.toJson(responseVo));
    }

    @Test
    void list() {
        ResponseVo<PageInfo> list = orderService.list(12, 1, 10);
        log.info("list = {}",gson.toJson(list));
    }

    @Test
    void detail() {
        Long no = 1585191616943L;
        ResponseVo<OrderVo> responseVo = orderService.detail(12, no);
        log.info("detail = {}",gson.toJson(responseVo));
    }

    @Test
    void cancel() {
        Long no = 1585191616943L;
        ResponseVo responseVo = orderService.cancel(12, no);
        log.info("detail = {}",gson.toJson(responseVo));
    }
}