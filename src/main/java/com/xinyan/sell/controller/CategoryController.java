package com.xinyan.sell.controller;

import com.xinyan.sell.po.ProductCategory;
import com.xinyan.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

/**
 * Administrator
 * 2018/11/18 0018
 */
@Controller
@RequestMapping("/seller/category")
public class CategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    //============= 转发页面 =============

    /**
     * 转发到添加类目页面
     * @return
     */
    @RequestMapping("/addCategoryPage")
    public String addCategory(){
        return "Category/addCategory";
    }

    /**
     * 转发到类目修改页面
     * @param categoryId
     * @param map
     * @return
     */
    @RequestMapping("/updateCategoryPage")
    public String updateCategory(@RequestParam() Integer categoryId,Map<String,Object> map){
        ProductCategory productCategory = productCategoryService.findOne(categoryId);
        map.put("productCategory", productCategory);
//        model.addAttribute("productCategory",productCategory);
        return "Category/updateCategory";
    }

    //=============  方法  ==================

    /**
     * 类目类型列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                       @RequestParam(value = "size",required = false,defaultValue = "5")Integer size,
                       Map<String,Object> map){
        //创建一个空分页对象，运用方法查询数据库放入分页对象中
        PageRequest pageRequest = new PageRequest(page -1 ,size);
        Page<ProductCategory> productCategoryPage = productCategoryService.findList(pageRequest);
        //把获得的分页对象放入map提供给页面查询
        map.put("productCategoryPage",productCategoryPage);
        return "Category/categoryList";
    }

    /**
     * 添加类目类型
     * @param productCategory
     * @return
     */
    @PostMapping("/doAddCategory")
    public String addCategory(@Valid ProductCategory productCategory) {
        productCategoryService.saveCategory(productCategory);
        return "redirect:list";
    }

    /**
     * 修改类目
     * @param productCategory
     * @return
     */
    @PostMapping("/updateCategory")
    public String updateCategory(ProductCategory productCategory){
        productCategoryService.updateProductCategory(productCategory);
        return "redirect:/seller/category/list";
    }
}
