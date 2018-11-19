package com.xinyan.sell.service.impl;

import com.xinyan.sell.converter.OrderMasterToOrderDTOConverter;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Administrator
 * 2018/11/16
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);

        //进行组装
        OrderDto orderDto = new OrderDto();
        //将查询的数据放到Dto中
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    /**
     * 订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMasterToOrderDTOConverter.converter(orderMasterPage.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList, pageable, orderMasterPage.getTotalElements());
        return orderDtoPage;
    }
}
