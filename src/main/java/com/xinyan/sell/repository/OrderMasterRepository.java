package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Nico
 * 2018/11/16
 *
 * 订单主表的的 Repository 接口
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
