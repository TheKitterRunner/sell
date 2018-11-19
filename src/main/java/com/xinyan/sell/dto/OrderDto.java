package com.xinyan.sell.dto;

import com.xinyan.sell.po.OrderDetail;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Nico
 * 2018/11/16
 *
 * 订单信息传输的类(用于在Controller层和Service层)
 */
@Data
public class OrderDto {

    /** 订单id */
    private String orderId;

    /**卖家名字*/
    private String buyerName;

    /**卖家电话*/
    private String buyerPhone;

    /**卖家地址*/
    private String buyerAddress;

    /**卖家微信openid*/
    private String buyerOpenid;

    /**订单总额*/
    private BigDecimal orderAmount;

    /**订单状态 默认为下单状态*/
    private Integer orderStatus;

    /**支付状态 默认支付状态*/
    private Integer payStatus;

    /**创建时间*/
    private Date createTime;

    /**更新(修改时间)*/
    private Date updateTime;

    /** 订单详情的列表 */
    private List<OrderDetail> orderDetailList;
}
