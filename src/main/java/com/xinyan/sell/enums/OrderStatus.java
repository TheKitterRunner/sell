package com.xinyan.sell.enums;

import lombok.Getter;

/**
 * 3061
 * 2018/11/14
 * 订单详情
 */
@Getter
public enum OrderStatus {

    NEW_ORDER(0, "新订单"),
    FINISHED(1, "已完结"),
    CANCEL(2, "已取消")
    ;

    private Integer code;
    private String message;

    OrderStatus(Integer code, String message) {

        this.code = code;
        this.message = message;
    }
}
