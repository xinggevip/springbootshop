package com.qiangssvip.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiangssvip.dao.ProductMapper;
import com.qiangssvip.enums.ResponseEnum;
import com.qiangssvip.pojo.Product;
import com.qiangssvip.service.ICategory;
import com.qiangssvip.service.IProductService;
import com.qiangssvip.service.vo.ProductDetailVo;
import com.qiangssvip.service.vo.ProductVo;
import com.qiangssvip.service.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.qiangssvip.enums.ProductStatusEnum.DELETE;
import static com.qiangssvip.enums.ProductStatusEnum.OFF_SALE;

@Service
@Transactional
@Slf4j
public class IProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategory iCategory;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        HashSet<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            iCategory.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);

        List<ProductVo> collect = products.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(products);
        pageInfo.setList(collect);

        return ResponseVo.successs(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);

        log.info("Status = {}",product.getStatus());

        if (product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        // product.setStock(product.getStock() > 100 ? 100 : product.getStock()); // 敏感数据处理

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        return ResponseVo.successs(productDetailVo);
    }
}
