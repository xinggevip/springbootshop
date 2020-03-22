package com.qiangssvip.service.impl;

import com.qiangssvip.service.ICategory;
import com.qiangssvip.service.vo.CategoryVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class ICategoryImplTest {

    @Autowired
    private ICategory iCategory;

    @Test
    void selectAll() {
        ResponseVo<List<CategoryVo>> selectAll = iCategory.selectAll();
        List<CategoryVo> data = selectAll.getData();
        for (CategoryVo datum : data) {
            System.out.println(datum);
        }
    }

    @Test
    void findSubCategoryId() {
        HashSet<Integer> set = new HashSet<>();
        iCategory.findSubCategoryId(100001,set);
        log.info("set={}",set);
    }
}