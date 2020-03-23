package com.qiangssvip.service.impl;

import com.qiangssvip.form.CartAddForm;
import com.qiangssvip.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ICartServiceImplTest {

    @Autowired
    private ICartService iCartService;

    @Test
    void add() {
        iCartService.add(1,new CartAddForm(27,true));
    }
}