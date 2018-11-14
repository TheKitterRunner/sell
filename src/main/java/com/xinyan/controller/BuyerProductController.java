package com.xinyan.controller;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xinyan.sell.po.ProductCategory;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.service.ProductCategoryService;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Nico
 * 2018/11/14
 *
 * 买家的 Controller
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/list")
    public ResultVo list(){
        // 获取所有的商品的信息
        List<ProductInfo> productInfoList = productService.findAll();

        // 在查询出所有的类目(根据类目的编号的list)
        // 1.获取所有的商品的类目的类型列表
//        List<ProductCategory> productCategoryList = productCategoryService.findAll();
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo : productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        // JDK8 新特性 Lamdam 表达式
        List<Integer> categoryTypeList = productInfoList.stream().map(e ->e.getCategoryType() ).collect(Collectors.toList());

        // 2.根据类型列表查出对应的类目明信息
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeList(categoryTypeList);

        // 再将得到的数据拼装
        // 第一级别(ResultOV)
        ResultVo resultVo = new ResultVo();

        return null;
    }
}
