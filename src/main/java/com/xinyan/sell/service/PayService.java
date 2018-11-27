package com.xinyan.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.xinyan.sell.dto.OrderDto;

/**
 * Nico
 * 2018/11/24
 * 微信支付的接口
 */
public interface PayService {

    PayResponse create(OrderDto orderDto);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDto orderDto);
}
