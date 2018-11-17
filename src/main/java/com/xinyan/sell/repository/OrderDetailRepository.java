package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Nico
 * 2018/11/16
 *
 * 订单详情的 Repository 接口
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 根据订单的id查询订单详情
     * @param orderid
     * @return
     */
    List<OrderDetail> findByOrderId(String orderid);
}
