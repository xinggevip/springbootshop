package com.qiangssvip.service.impl;

import com.qiangssvip.dao.CategoryMapper;
import com.qiangssvip.pojo.Category;
import com.qiangssvip.service.ICategory;
import com.qiangssvip.service.vo.CategoryVo;
import com.qiangssvip.service.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ICategoryImpl implements ICategory {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        ArrayList<CategoryVo> arrayList = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();
        for (Category category : categories) {
            if (category.getParentId().equals(0)) {
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                arrayList.add(categoryVo);
            }
        }
        return ResponseVo.successs(arrayList);
    }
}
