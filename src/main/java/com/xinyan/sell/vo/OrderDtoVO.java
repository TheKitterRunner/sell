package com.xinyan.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyan.sell.po.OrderDetail;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Nico
 * 2018/11/18
 *
 * 订单对应vo
 */
@Data
public class OrderDtoVO {

    @Id
    @JsonProperty("orderId")
    private String orderId;

    /**卖家名字*/
    @JsonProperty("buyerName")
    private String buyerName;

    /**卖家电话*/
    @JsonProperty("buyerPhone")
    private String buyerPhone;

    /**卖家地址*/
    @JsonProperty("buyerAddress")
    private String buyerAddress;

    /**卖家微信openid*/
    @JsonProperty("buyerOpenid")
    private String buyerOpenid;

    /**订单总额*/
    @JsonProperty("orderAmount")
    private BigDecimal orderAmount;

    /**订单状态 默认为下单状态*/
    @JsonProperty("orderStatus")
    private Integer orderStatus;

    /**支付状态 默认支付状态*/
    @JsonProperty("payStatus")
    private Integer payStatus;

    /**创建时间*/
    @JsonProperty("createTime")
    private Date createTime;

    /**更新(修改时间)*/
    @JsonProperty("updateTime")
    private Date updateTime;

    /** 订单详情的列表 */
    @JsonProperty("orderDetailList")
    private List<OrderDetailVO> orderDetailVOList;
}
