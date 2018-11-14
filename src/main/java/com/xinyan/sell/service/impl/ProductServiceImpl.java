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

    @Override
    public void saveProduct() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(KeyUtil.generatedUniqueKey());
        productInfo.setProductName("凉皮");
        productInfo.setProductPrice(new BigDecimal(20));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("正宗陕西臊子面");
        productInfo.setProductIcon("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2591780415,1191633011&fm=26&gp=0.jpg");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(10);
        System.out.println(productInfo);
        ProductInfo productInfo1 = productRepository.save(productInfo);
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
}
