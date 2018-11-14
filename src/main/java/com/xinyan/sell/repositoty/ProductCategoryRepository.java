package com.xinyan.sell.repositoty;

import com.xinyan.sell.po.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 3061
 * 2018/11/14
 * 商品类目的 Repository 接口
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * 查询多个类目
     * @param categoryTypeList
     * @return
     */
    public List<ProductCategory> findByCategoryIdIn(List<Integer> categoryTypeList);
}
