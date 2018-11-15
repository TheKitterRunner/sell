package com.xinyan.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Nico
 * 2018/11/14
 * 商品详细信息的 VO 对象
 */
@Data
public class ProductInfoVO {

    /** 商品id属性名的转换 */
    @JsonProperty("id")
    private String productId;

    /** 商品名称*/
    @JsonProperty("name")
    private String productName;

    /** 商品价格 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 商品描述*/
    @JsonProperty("description")
    private String productDescription;

    /** 商品图片 */
    @JsonProperty("icon")
    private String productIcon;
}
