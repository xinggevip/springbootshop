package com.qiangssvip.dao;

import com.qiangssvip.pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void findById() {
    }

    @Test
    void queryById() {
        Category category = categoryMapper.queryById(100002);
        System.out.println(category);
    }
}