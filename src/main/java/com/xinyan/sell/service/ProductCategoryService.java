package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Nico
 * 2018/11/14
 * 类目的业务接口
 */
public interface ProductCategoryService {

    // 买家端

    /**
     * 查询所有类目
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据类型的列表查询多个
     * @param productCategoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeList(List<Integer> productCategoryTypeList);

    /**
     * 查询单个类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);


    //============= 卖家端 ================

    /**
     * 类目类型的分页查询列表
     * @param pageable
     * @return
     */
    Page<ProductCategory> findList(Pageable pageable);

    /**
     * 添加类目
     * @param productCategory
     * @return
     */
    ProductCategory saveCategory(ProductCategory productCategory);

    /**
     * 修改类目
     * @param productCategory
     */
    void updateProductCategory (ProductCategory productCategory);
}
