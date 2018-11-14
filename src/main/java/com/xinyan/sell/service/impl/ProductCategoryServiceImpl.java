package com.xinyan.sell.service.impl;

import com.xinyan.sell.po.ProductCategory;
import com.xinyan.sell.repositoty.ProductCategoryRepository;
import com.xinyan.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Nico
 * 2018/11/14
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeList(List<Integer> productCategoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(productCategoryTypeList);
    }

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryRepository.findOne(categoryId);
    }
}
