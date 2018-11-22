package com.xinyan.sell.dto;

import lombok.Getter;

/**
 * Nico
 * 2018/11/16
 *
 * 购物车类
 */
@Getter
public class CartDto {

    private String productId;

    private Integer quantity;

    public CartDto(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
