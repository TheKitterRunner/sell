package com.xinyan.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Nico
 * 2018/11/18
 */
@Data
public class OrderDetailVO {

    /** 订单详情编号 */
    @Id
    @JsonProperty("detailId")
    private String detailId;

    /** 订单的Id */
    @JsonProperty("orderId")
    private String orderId;

    /** 商品Id */
    @JsonProperty("productId")
    private String productId;

    /** 商品名称 */
    @JsonProperty("productName")
    private String productName;

    /** 当前价格，单位分 */
    @JsonProperty("productPrice")
    private BigDecimal productPrice;

    /** 数量 */
    @JsonProperty("productQuantity")
    private Integer productQuantity;

    /** 菜单小图标 */
    @JsonProperty("productIcon")
    private String productIcon;

    /** 创建时间 */
    @JsonProperty("createTime")
    private Date createTime;

    /** 更新(修改时间) */
    @JsonProperty("updateTime")
    private Date updateTime;
}
