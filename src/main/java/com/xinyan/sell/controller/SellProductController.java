package com.xinyan.sell.controller;

import com.xinyan.sell.po.ProductCategory;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.service.ProductCategoryService;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Administrator
 * 2018/11/16 0016
 *
 * 卖家端的Controller
 */
@Controller
@RequestMapping("seller/product")
public class SellProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    //============ 转发页面 ================

    /**
     * 转发到商品添加页面
     * @return
     */
    @RequestMapping("/addProductPage")
    public String addProduct(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                             @RequestParam(value = "size",required = false,defaultValue = "5")Integer size,
                             Map<String,Object> map){
        //通过方法查找类目的列表放入map集合提供给页面使用
        PageRequest pageRequest = new PageRequest(page - 1 ,size);
        Page<ProductCategory> productCategoryPage = productCategoryService.findList(pageRequest);
        map.put("productCategoryPage",productCategoryPage);
        return "Product/addProduct";
    }

    /**
     * 转发到商品修改页面
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/updateProductPage")
    public String updateProduct(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = false,defaultValue = "5")Integer size,
                                @RequestParam() String productId,Map<String,Object> map){
        //通过方法查找类目的列表放入map集合提供给页面使用
        PageRequest pageRequest = new PageRequest(page -1 ,size);
        Page<ProductCategory> productCategoryPage = productCategoryService.findList(pageRequest);
        map.put("productCategoryPage",productCategoryPage);
        //根据id获得一条商品数据
        ProductInfo productInfo = productService.findOneProductById(productId);
        //把根据id获得的商品数据放入到map集合中
        map.put("productInfo",productInfo);
        return "Product/updateProduct";
    }

    //=========== 方法 ============

    /**
     * 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                       @RequestParam(value = "size",required = false,defaultValue = "5") Integer size,
                       Map<String,Object> map, Map<String,Object> categoryMap, Model model){
        //创建一个空分页对象，运用方法查询数据库放入分页对象中
        PageRequest pageRequest = new PageRequest(page - 1 ,size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        Page<ProductCategory> productCategoryPage = productCategoryService.findList(pageRequest);
        categoryMap.put("productCategoryPage",productCategoryPage);
        //把获得的分页对象放入map提供给页面查询
        map.put("productInfoPage",productInfoPage);
        return "Product/productList";
    }

    /**
     * 添加商品
     * @return
     */
    @PostMapping("/doAdd")
    public String add(ProductInfo productInfo) {
        //获得一个productId
        productInfo.setProductId(KeyUtil.generatedUniqueKey());
        productService.saveProduct(productInfo);
        return "redirect:/product/list";
    }

    /**
     * 修改商品
     * @param productInfo
     * @return
     */
    @PostMapping("/updateProduct")
    public String update(ProductInfo productInfo){
        //调用修改方法
        productService.saveUpdateProduct(productInfo);

        return "redirect:/product/list";
    }

    /**
     * 修改商品状态(上架)
     * @param productId
     * @return
     */
    @GetMapping("/detail")
    public String detail(String productId){
        productService.updateProducrStatus(productId);
        return "redirect:/product/list";
    }

    /**
     * 修改商品状态(下架)
     * @param productId
     * @return
     */
    @GetMapping("/cancel")
    public String cancel(String productId){
        productService.updateProducrStatus(productId);
        return "redirect:/product/list";
    }
}
