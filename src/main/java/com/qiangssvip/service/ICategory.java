package com.qiangssvip.service;

import com.qiangssvip.service.vo.CategoryVo;
import com.qiangssvip.service.vo.ResponseVo;

import java.util.List;

public interface ICategory {
    ResponseVo<List<CategoryVo>> selectAll();
}
