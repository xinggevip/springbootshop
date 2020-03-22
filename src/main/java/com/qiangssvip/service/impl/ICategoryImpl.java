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
import java.util.stream.Collectors;

@Service
@Transactional
public class ICategoryImpl implements ICategory {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
//        ArrayList<CategoryVo> arrayList = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();
//        for (Category category : categories) {
//            if (category.getParentId().equals(0)) {
//                CategoryVo categoryVo = new CategoryVo();
//                BeanUtils.copyProperties(category,categoryVo);
//                arrayList.add(categoryVo);
//            }
//        }
        // lambda + stream
        List<CategoryVo> collect = categories.stream()
                .filter(e -> e.getParentId().equals(0))  // 返回符合条件的数据
                .map(this::category2CategoryVo)         // 遍历数据
//                .map(e -> category2CategoryVo(e))     // 和上一句效果一致，idea推荐使用上面的写法
                .collect(Collectors.toList());
        findSubCategory(collect,categories);

        return ResponseVo.successs(collect);
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    private void findSubCategory(List<CategoryVo> categoryVoList,List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
            ArrayList<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                if (category.getParentId().equals(categoryVo.getId())) {
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
            }
            categoryVo.setSubCategories(subCategoryVoList);
            findSubCategory(subCategoryVoList,categories);
        }
    }


}
