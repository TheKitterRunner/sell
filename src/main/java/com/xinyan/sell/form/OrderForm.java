package com.xinyan.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 封装订单表单参数
 */
@Data
public class OrderForm {

    /** 姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;

    /** 电话 */
    @NotEmpty(message = "电话必填")
    private String phone;

    /** 地址 */
    @NotEmpty(message = "地址必填")
    private String address;

    /** 微信openid */
    @NotEmpty(message = "微信openid必填")
    private String openid;

    /** 订单详情 */
    @NotEmpty(message = "订单详情必填")
    private String items;
}
