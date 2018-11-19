package com.xinyan.sell.converter;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Administrator
 * 2018/11/16
 */
public class OrderMasterToOrderDTOConverter {

    private OrderMasterToOrderDTOConverter(){

    }

    public static OrderDto converter(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    public static List<OrderDto> converter(List<OrderMaster> orderMasterList){
        List<OrderDto> orderDTOList = orderMasterList.stream()
                .map(e -> converter(e)).collect(Collectors.toList());
        return orderDTOList;
    }
}
