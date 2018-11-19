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
 */

@Data
@DynamicUpdate
@Entity
public class OrderDetail {

    /** 订单详情编号 */
    @Id
    private String detailId;

    /** 订单的Id */
    private String orderId;

    /** 商品Id */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 当前价格，单位分
     */
    private BigDecimal productPrice;

    /**
     * 数量
     */
    private Integer productQuantity;

    /**
     * 菜单小图标
     */
    private String productIcon;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新(修改时间)
     */
    private Date updateTime;
}
