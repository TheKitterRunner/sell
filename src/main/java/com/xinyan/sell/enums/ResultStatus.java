package com.xinyan.sell.enums;

import lombok.Getter;

/**
 * 3061
 * 2018/11/14
 * 结果状态
 */
@Getter
public enum ResultStatus {

    SUCCESS(0,"成功"),

    FORM_PARAM_ERROR(5,"表单参数有误"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足"),

    ORDER_PARAM_ERROR(20,"订单参数错误"),
    ORDER_NOT_EXIST(21, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(22, "订单详情不存在"),
    ORDER_STATUS_ERROR(23,"订单状态错误"),
    ORDER_UPDATE_FAIL(24,"订单更新失败"),
    ORDER_PAY_STATUS_ERROR(25,"订单支付状态错误"),
    ORDER_OWNER_ERROR(26,"该订单不属于当前用户"),
    ORDER_CANCEL_SUCCESS(27,"订单取消成功"),
    ORDER_FINISH_SUCCESS(28,"订单完结成功"),

    CART_EMPTY(30,"购物车为空"),

    WECHAT_MP_ERROR(40,"微信公众号异常"),
    WECHAT_MP_AUTHORIZE_ERROR(41,"微信公众号授权异常"),
    WECHAT_MP_PAY_NOTIFY_MONEY_ERROR(42,"微信支付异步通知金额错误");

    private Integer code;
    private String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
