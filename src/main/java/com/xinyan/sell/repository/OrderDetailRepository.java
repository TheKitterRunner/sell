package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Administrator
 * 2018/11/13
 * 订单详情Repository接口
 * 写完要进行测试
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 订单查询
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
