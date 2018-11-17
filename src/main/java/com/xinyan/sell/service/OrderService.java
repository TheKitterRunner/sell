package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Nico
 * 2018/11/16
 *
 * 订单管理的Service层
 */
public interface OrderService {

    // 创建订单
    OrderDto createOrder(OrderDto orderDto);

    // 查询单个订单
    OrderDto findOne(String orderId);

    // 查询所有订单
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    // 取消订单
    OrderDto cancelOrder(OrderDto orderDto);

    // 支付订单
    OrderDto finish(OrderDto orderDto);


}
