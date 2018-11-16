package com.xinyan.sell.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Nico
 * 2018/11/16
 *
 * 订单主表对应的 po类
 */
@Data
@DynamicUpdate
@Entity
public class OrderMaster {

    @Id
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
}
