package com.xinyan.sell.repository;

import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * productInfo 商品信息的测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    //测试添加
    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(KeyUtil.generatedUniqueKey());
        productInfo.setProductName("凉皮");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("正宗陕西凉皮,滑嫩可口");
        productInfo.setProductIcon("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3362030009,4020392457&fm=26&gp=0.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(12);
        System.out.println(productInfo);
        ProductInfo result = productRepository.save(productInfo);
        Assert.assertNotNull(result);
    }

    // 查询单个
    @Test
    public void findOneTest(){
        ProductInfo productInfo = productRepository.findOne("1402d34eb143459f90a544ff38df1ba9");
        Assert.assertNotNull(productInfo);
    }

    // 修改指定商品的信息
    @Test
    public void updateTest(){
        ProductInfo productInfo = productRepository.findOne("1402d34eb143459f90a544ff38df1ba9");
        productInfo.setProductName("老潼关肉夹馍");
        ProductInfo productInfo1 = productRepository.save(productInfo);
        Assert.assertNotNull(productInfo1);
    }

    //根据商品状态查询商品
    @Test
    public void findProductByStatusTest(){
        List<ProductInfo> productInfos = productRepository.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfos.size());
    }

    // 测试删除的方法
    @Test
    public void deleteTest(){
        productRepository.delete("1402d34eb143459f90a544ff38df1ba9");
    }

    // 根据商品id的集合查询
    @Test
    public void findByIds(){
        List<ProductInfo> productInfoList = productRepository
                .findByProductIdIn(
                        Arrays.asList("97a03a96577242408eb9eabefd6dd29d"));
        Assert.assertNotEquals(0, productInfoList);
    }
}