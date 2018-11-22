package com.xinyan.sell.converter;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Administrator
 * 转换类
 *  2018/11/18 0018
 */
public class OrderMasterToOrderDTOConverter {
    public OrderMasterToOrderDTOConverter() {

    }

    public static OrderDto converter(OrderMaster orderMaster) {
        OrderDto orderDTO = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }


    public static List<OrderDto> converter(List<OrderMaster> orderMasterList) {
        List<OrderDto> orderDTOList = orderMasterList.stream()
                .map(e -> converter(e)).collect(Collectors.toList());
        return orderDTOList;
    }
}
