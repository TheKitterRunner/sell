package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.rmi.PortableRemoteObject;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * ProductCategoryService 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    // 测试查询单个
    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertNotNull(productCategory);
    }

    // 测试查询多个
    @Test
    public void findByTypesTest(){
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeList(Arrays.asList(10,11));
        Assert.assertNotNull(productCategoryList);
    }

    @Test
    public void findAll(){
        List<ProductCategory> productCategorys = productCategoryService.findAll();
        System.out.println(productCategorys);
    }

}