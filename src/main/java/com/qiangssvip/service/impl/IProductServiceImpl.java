package com.qiangssvip.service.impl;

import com.qiangssvip.dao.ProductMapper;
import com.qiangssvip.pojo.Product;
import com.qiangssvip.service.ICategory;
import com.qiangssvip.service.IProductService;
import com.qiangssvip.service.vo.ProductVo;
import com.qiangssvip.service.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class IProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategory iCategory;

    @Override
    public ResponseVo<List<ProductVo>> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        HashSet<Integer> categoryIdSet = new HashSet<>();
        iCategory.findSubCategoryId(categoryId,categoryIdSet);
        categoryIdSet.add(categoryId);

        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        for (Product product : products) {
            System.out.println(product);
        }

        return null;
    }
}
