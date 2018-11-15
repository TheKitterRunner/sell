package com.xinyan.sell.controller;

import com.xinyan.sell.po.ProductCategory;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.service.ProductCategoryService;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.vo.ProductCategoryVO;
import com.xinyan.sell.vo.ProductInfoVO;
import com.xinyan.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 获取商品信息的列表
     * @return
     */
    @GetMapping("/list")
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
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList){
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            //  将后后台商品类目的信息转换成视图层展示的信息
            BeanUtils.copyProperties(productCategory, productCategoryVO);

            // 第二层级
            List<ProductInfoVO> productInfoVOList =new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                // 如果商品的类目信息和对应的类目信息匹配,则添加到该种类目的list中
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            // 将商品信息的视图层list赋值给商品类目地图层对象的productInfoVOList属性
            productCategoryVO.setProductInfoVOList(productInfoVOList);
            productCategoryVOList.add(productCategoryVO);
        }

        resultVo.setCode(0);
        resultVo.setMsg("获取商品列表信息成功");
        resultVo.setData(productCategoryVOList);

        // 返回信息到视图层
        return resultVo;
    }
}
