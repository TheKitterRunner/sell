package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *  订单业务接口(买家端)
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    public OrderDto createOrder(OrderDto orderDto);

    /**
     * 查询用户订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    public OrderDto findOne(String orderId);
}
