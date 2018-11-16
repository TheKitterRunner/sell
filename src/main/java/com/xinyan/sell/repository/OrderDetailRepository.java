package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Nico
 * 2018/11/16
 *
 * 订单详情的 Repository 接口
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
