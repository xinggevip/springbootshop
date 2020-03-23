package com.qiangssvip.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qiangssvip.form.CartAddForm;
import com.qiangssvip.form.CartUpdateForm;
import com.qiangssvip.service.ICartService;
import com.qiangssvip.service.vo.CartVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ICartServiceImplTest {

    @Autowired
    private ICartService iCartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    void add() {
        ResponseVo<CartVo> responseVo = iCartService.add(1, new CartAddForm(29, true));
        log.info("cartVo = {}",gson.toJson(responseVo.getData()));
    }

    @Test
    void list() {
        ResponseVo<CartVo> list = iCartService.list(1);
        log.info("cartVo = {}",gson.toJson(list.getData()));
    }

    @Test
    void update() {
        CartUpdateForm form = new CartUpdateForm(2,false);
        ResponseVo<CartVo> responseVo = iCartService.update(1, 27, form);
        log.info("list = {}",gson.toJson(responseVo));

    }

    @Test
    void delete() {
        ResponseVo<CartVo> responseVo = iCartService.delete(1, 26);
        log.info("list = {}",gson.toJson(responseVo));

    }

    @Test
    void selectAll(){
        ResponseVo<CartVo> responseVo = iCartService.selectAll(1);
        log.info("list = {}",gson.toJson(responseVo));
    };

    @Test
    void unSelectAll(){
        ResponseVo<CartVo> responseVo = iCartService.unSelectAll(1);
        log.info("list = {}",gson.toJson(responseVo));
    };

    @Test
    void sum(){
        ResponseVo<Integer> responseVo = iCartService.sum(1);
        log.info("list = {}",gson.toJson(responseVo));
    };
}