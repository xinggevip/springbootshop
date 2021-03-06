package com.qiangssvip.service;

import com.github.pagehelper.PageInfo;
import com.qiangssvip.service.vo.ProductDetailVo;
import com.qiangssvip.service.vo.ResponseVo;

public interface IProductService {
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer id);
}
