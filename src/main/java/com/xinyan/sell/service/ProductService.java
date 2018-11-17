package com.xinyan.sell.service;

import com.xinyan.sell.dto.CartDto;
import com.xinyan.sell.po.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Nico
 * 2018/11/14
 *
 * 卖家端的ProductService 层
 */
public interface ProductService {

    /**
     * 添加商品
     */
    ProductInfo saveProduct(ProductInfo productInfo);

    /**
     * 根据id删除商品
     */
    void deleteProduct(String productId);

    /**
     * 更新商品信息
     * @param productId
     */
    void updateProduct(String productId);

    /**
     * 根据id查询商品
     * @param productId
     * @return
     */
    ProductInfo findOneProductById(String productId);

    /**
     * 根据商品状态查询
     * @param productId
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productId);

    /**
     * 根据商品的id 的集合查询
     * @param productIdList
     * @return
     */
    List<ProductInfo> findByProductIdList(List<String> productIdList);

    /**
     * 商品列表
     * @return
     */
    List<ProductInfo> findAll();

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 更新库存
     * @param cartDtoList
     */
    void decreaseStock(List<CartDto> cartDtoList);
}
