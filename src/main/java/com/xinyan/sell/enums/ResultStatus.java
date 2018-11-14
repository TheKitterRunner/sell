package com.xinyan.sell.enums;

import lombok.Getter;

/**
 * 3061
 * 2018/11/14
 * 结果状态
 */
@Getter
public enum ResultStatus {

    PRODUCT_NOT_EXIST(0, "商品不存在"),
    PRODUCT_STOCK_ERROR(1, "商品库存不足"),
    ORDER_NOT_EXIST(2, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(3, "订单详情不存在")
    ;

    private Integer code;
    private String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
