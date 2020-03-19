package com.qiangssvip;

import com.qiangssvip.dao.CategoryMapper;
import com.qiangssvip.pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
        Category category = categoryMapper.findById(100001);
        System.out.println(category);
    }

    @Test
    public void queryByIdTest(){
        Category category = categoryMapper.queryById(100002);
        System.out.println(category);
    }
}
