package com.qiangssvip;

import com.qiangssvip.dao.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {

    }


}
