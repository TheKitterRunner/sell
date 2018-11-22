package com.xinyan.sell.service.impl;

import com.xinyan.sell.dto.CartDto;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.ProductRepository;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Override
    public ProductInfo saveProduct(ProductInfo productInfo) {
        ProductInfo productInfo1 = productRepository.save(productInfo);
        return productInfo1;
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.delete(productId);
    }

    /**
     * 未完成
     * @param productId
     */
    @Override
    public void updateProduct(String productId) {
        ProductInfo productInfo = productRepository.findOne(productId);
    }

    @Override
    public ProductInfo findOneProductById(String productId) {
        return productRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productId) {
        return productRepository.findByProductStatus(productId);
    }

    @Override
    public List<ProductInfo> findByProductIdList(List<String> productIdList) {
        return productRepository.findByProductIdIn(productIdList);
    }

    @Override
    public List<ProductInfo> findAll() {
        return productRepository.findAll();
    }

    /**
     * 分页查询(买家端)
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(),pageable.getPageSize());
        Page<ProductInfo> productInfoPage = productRepository.findAll(pageable);
        return productInfoPage;
    }

//
//    /**
//     * 分页查询(卖家端)
//     * @param pageable
//     * @return
//     */
//    @Override
//    public Page<ProductInfo> findList(Pageable pageable) {
//        Page<ProductInfo> productInfoPage = productRepository.findAll(pageable);
//        return productInfoPage;
//    }

    /**
     * 修改商品状态(上,下架)
     * @param productId
     */
    @Override
    public void updateProducrStatus(String productId) {
        //根据ID获取要修改的单条数据
        ProductInfo productInfo = productRepository.findOne(productId);
        //获得数据中的商品状态
        Integer productStatus = productInfo.getProductStatus();
        //判断商品状态，如果状态为0(上架)，就修改为1(下架)
        if (productStatus.equals(0)){
            productInfo.setProductStatus(1);
            productService.saveProduct(productInfo);
        //反之，如果状态为1(下架) ，就修改为0(上架)
        }else {
            productInfo.setProductStatus(0);
            productService.saveProduct(productInfo);
        }
    }

    /**
     * 修改并保存商品
     * @param productInfo
     */
    @Override
    public void saveUpdateProduct(ProductInfo productInfo) {
        productRepository.save(productInfo);
    }


    /**
     * 减少库存
     * @param cartDtoList
     */
    @Override
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList){
            ProductInfo productInfo = productRepository.findOne(cartDto.getProductId());
            if (productInfo == null){
                log.info("更新库存商品不存在, productID = " + productInfo.getProductId());
            }

            // 当前的库存减掉传递过来的订单中对应商品的数量
            productInfo.setProductStock(productInfo.getProductStock() - cartDto.getQuantity());
            productRepository.save(productInfo);
        }
    }

    /**
     * 增加库存
     * @param cartDtoList
     */
    @Override
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList){
            ProductInfo productInfo = productRepository.findOne(cartDto.getProductId());
            if (productInfo == null){
                log.info("更新库存商品不存在, productID = " + productInfo.getProductId());
            }

            // 当前的库存加上传递过来的订单中对应商品的数量
            productInfo.setProductStock(productInfo.getProductStock() + cartDto.getQuantity());
            productRepository.save(productInfo);
        }
    }
}
