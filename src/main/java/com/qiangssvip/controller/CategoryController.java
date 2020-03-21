package com.qiangssvip.controller;

import com.qiangssvip.service.ICategory;
import com.qiangssvip.service.vo.CategoryVo;
import com.qiangssvip.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategory iCategory;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll() {
        return iCategory.selectAll();
    }


}
