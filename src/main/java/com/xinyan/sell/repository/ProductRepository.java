package com.xinyan.sell.repository;

import com.xinyan.sell.po.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Nico
 * 2018/11/14
 * ProductRepository 的dao接口
 */
public interface ProductRepository extends JpaRepository<ProductInfo, String > {

    /**
     * 根据商品状态查询商品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 根据商品的id 的集合查询
     */
    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
