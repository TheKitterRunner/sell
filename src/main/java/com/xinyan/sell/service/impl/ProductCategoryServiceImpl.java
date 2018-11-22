package com.xinyan.sell.service.impl;

import com.xinyan.sell.po.ProductCategory;
import com.xinyan.sell.repository.ProductCategoryRepository;
import com.xinyan.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    /**
     * 分页查询类目
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductCategory> findList(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageable);
        return productCategoryPage;
    }

    /**
     * 保存类目
     * @param productCategory
     * @return
     */
    @Override
    public ProductCategory saveCategory(ProductCategory productCategory) {
        ProductCategory productCategory1 = productCategoryRepository.save(productCategory);
        return productCategory1;
    }

    /**
     * 修改类目
     * @param productCategory
     */
    @Override
    public void updateProductCategory(ProductCategory productCategory) {
        ProductCategory productCategory1 = productCategoryRepository.save(productCategory);
    }
}
