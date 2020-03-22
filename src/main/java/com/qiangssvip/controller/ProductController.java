package com.qiangssvip.controller;

import com.github.pagehelper.PageInfo;
import com.qiangssvip.service.IProductService;
import com.qiangssvip.service.vo.ProductDetailVo;
import com.qiangssvip.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize) {
        ResponseVo<PageInfo> list = iProductService.list(categoryId, pageNum, pageSize);
        return list;
    }

    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId) {
        return iProductService.detail(productId);
    }


}
