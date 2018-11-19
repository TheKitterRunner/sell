package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Administrator
 * 2018/11/16
 */
public interface OrderService {

    public OrderDto findOne(String orderId);

    // ===== 卖家端 =====

    public Page<OrderDto> findList(Pageable pageable);

}
