package com.xinyan.sell.enums;

import lombok.Getter;

/**
 * 3061
 * 2018/11/14
 */
@Getter
public enum ProductStatus {
    UP(0, "上架"),
    down(2, "下架")
    ;

    private Integer code;
    private String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
