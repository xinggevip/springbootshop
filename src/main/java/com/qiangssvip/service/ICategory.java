package com.qiangssvip.service;

import com.qiangssvip.service.vo.CategoryVo;
import com.qiangssvip.service.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategory {
    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
