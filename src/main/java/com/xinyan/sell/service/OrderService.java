package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDto;

/**
 * Nico
 * 2018/11/16
 *
 * 订单管理的Service层
 */
public interface OrderService {

    public OrderDto createOrder(OrderDto orderDto);
}
