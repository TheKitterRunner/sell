package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.dto.OrderDtoTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    OrderDto finishOrder(OrderDto orderDto);

    /*==================卖家端============*/
    // 查询所有订单
    //Page<OrderDto> findList(Pageable pageable);

    //完结订单
    OrderDto finish(OrderDto orderDto);

    //订单查询(分页)
    Page<OrderDtoTO> findList(Pageable pageable);
}
