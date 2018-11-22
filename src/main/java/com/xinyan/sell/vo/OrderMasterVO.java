package com.xinyan.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.xinyan.sell.enums.OrderStatus;
import com.xinyan.sell.enums.PayStatus;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Nico
 * 2018/11/18
 */
@Data
public class OrderMasterVO {

    /** 订单id*/
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
    private Integer orderStatus = OrderStatus.NEW_ORDER.getCode();

    /**支付状态 默认支付状态*/
    @JsonProperty("payStatus")
    private Integer payStatus = PayStatus.WAIT.getCode();

    /**创建时间*/
    @JsonProperty("createTime")
    private Date createTime;

    /**更新(修改时间)*/
    @JsonProperty("updateTime")
    private Date updateTime;
}
