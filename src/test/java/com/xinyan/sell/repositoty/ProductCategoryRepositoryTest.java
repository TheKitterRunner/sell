package com.xinyan.sell.repositoty;

import com.xinyan.sell.po.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * ProductCategoryRepository 的单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    // 保存
    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(11);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotEquals(null, result);
    }

    // 查询单个
    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
//        System.out.println(productCategory);
//        Assert.assertNotEquals(null, productCategory);
        Assert.assertNotNull("根据id查类目", productCategory);
    }

    // 查询多个
    @Test
    public void findByCategoryTypeInTest(){
        List<ProductCategory> productCategoryList = repository.findByCategoryTypeIn(Arrays.asList(10, 11));
        Assert.assertNotNull("根据多个id查类目", productCategoryList);
    }

    // 修改
    @Test
    public void updateTest(){
        ProductCategory productCategory = repository.findOne(2);
        productCategory.setCategoryName("男神最爱");

        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }
}