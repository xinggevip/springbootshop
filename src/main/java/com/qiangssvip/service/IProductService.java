package com.qiangssvip.service;

import com.qiangssvip.service.vo.ProductVo;
import com.qiangssvip.service.vo.ResponseVo;

import java.util.List;

public interface IProductService {
    ResponseVo<List<ProductVo>> list(Integer categoryId,Integer pageNum,Integer pageSize);
}
