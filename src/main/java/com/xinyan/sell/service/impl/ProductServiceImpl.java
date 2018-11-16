package com.xinyan.sell.service.impl;

import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.ProductRepository;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * Nico
 * 2018/11/14
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 添加商品
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo saveProduct(ProductInfo productInfo) {
        ProductInfo productInfo1 = productRepository.save(productInfo);
        return productInfo;
    }

    /**
     * 根据id删除商品
     * @param productId
     */
    @Override
    public void deleteProduct(String productId) {
        productRepository.delete(productId);
    }

    /**
     * 更新商品信息
     * @param productId
     */
    @Override
    public void updateProduct(String productId) {
        ProductInfo productInfo = productRepository.findOne(productId);
    }

    /**
     * 根据商品id查询单个商品
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOneProductById(String productId) {
        return productRepository.findOne(productId);
    }

    /**
     * 根据商品状态查询
     * @param productId
     * @return
     */
    @Override
    public List<ProductInfo> findByProductStatus(Integer productId) {
        return productRepository.findByProductStatus(productId);
    }

    /**
     * 根据商品的id 的集合查询
     * @param productIdList
     * @return
     */
    @Override
    public List<ProductInfo> findByProductIdList(List<String> productIdList) {
        return productRepository.findByProductIdIn(productIdList);
    }

    /**
     * 商品列表
     * @return
     */
    @Override
    public List<ProductInfo> findAll() {
        return productRepository.findAll();
    }

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(),pageable.getPageSize());
        Page<ProductInfo> productInfoPage = productRepository.findAll(pageable);
        return productInfoPage;
    }

    /**
     * 减少商品库存
     */
}
