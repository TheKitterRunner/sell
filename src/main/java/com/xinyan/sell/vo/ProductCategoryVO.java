package com.xinyan.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Nico
 * 2018/11/14
 *
 * 商品类目 VO 实体类(包括商品类目)
 */
@Data
public class ProductCategoryVO {

    /** 类目 */
    @JsonProperty("name") //@JsonProperty 用于序列化json对象时修改属性名
    private String categoryName;

    /** 类目编号 */
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
