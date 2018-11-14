package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductCategory;
import org.springframework.stereotype.Service;

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
     * 查询单个
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);


}
