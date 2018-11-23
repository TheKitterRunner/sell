package com.xinyan.sell.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 3061
 * 2018/11/14
 * 商品类目的实体类
 */
@DynamicUpdate
@Entity
@Data
public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue
    /** 类目Id */
    private Integer categoryId;

    /** 类目名称 */
    @NotNull
    private String categoryName;

    /** 类目编号 */
    @NotNull
    private Integer categoryType;
}
