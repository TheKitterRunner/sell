package com.xinyan.sell.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Nico
 * 2018/11/14
 * 商品信息po类
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {

    @Id
    /** 商品ID */
    private String productId;

    /** 商品名 */
    private String productName;

    /** 价格 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 图片 */
    private String productIcon;

    /** 状态  0:上架 1下架 */
    private Integer productStatus = 0;

    /** 类目类型   */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;
}
