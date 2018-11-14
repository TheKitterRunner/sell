package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    // 测试添加
    @Test
    public void saveProductTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(KeyUtil.generatedUniqueKey());
        productInfo.setProductName("凉皮");
        productInfo.setProductPrice(new BigDecimal(20));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("正宗陕西臊子面");
        productInfo.setProductIcon("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2591780415,1191633011&fm=26&gp=0.jpg");
        productInfo.setCategoryType(10);
        ProductInfo productInfo1 = productService.saveProduct(productInfo);
        Assert.assertNotNull(productInfo1);
    }

    // 测试删除功能
    @Test
    public void deleteProductTest() {
        productService.deleteProduct("97a03a96577242408eb9eabefd6dd29d" );
    }

    // 测试通过id查找商品
    @Test
    public void findOneProductByIdTest() {
        ProductInfo productInfo = productService.findOneProductById("97a03a96577242408eb9eabefd6dd29d");
    }

    // 根据商品的状态查找
    @Test
    public void findByProductStatusTest() {
        List<ProductInfo> productlList = productService.findByProductStatus(1);
        Assert.assertEquals(0,productlList.size());
    }

    // 根据商品的id的集合查找
    @Test
    public void findByProductIdListTest() {
        List<ProductInfo> productInfoList = productService.findByProductIdList(Arrays.asList("97a03a96577242408eb9eabefd6dd29d"));
    }

    // 查询所有的shangpin
    @Test
    public void findAllTest() {
        List<ProductInfo> list = productService.findAll();
        Assert.assertNotEquals(0,list);
    }

    // 分页查询
    @Test
    public void findAllByPageTest() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> page  = productService.findAll(pageRequest);
        Assert.assertNotEquals(0,page.getTotalElements());
    }
}