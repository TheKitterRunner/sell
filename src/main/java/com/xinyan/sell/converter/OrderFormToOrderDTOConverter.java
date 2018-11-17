package com.xinyan.sell.converter;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.form.OrderForm;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.utils.JsonUtil;
import jdk.internal.org.objectweb.asm.TypeReference;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Slf4j
public class OrderFormToOrderDTOConverter {
    public static OrderDto converter(OrderForm orderForm){
        OrderDto orderDTO = new  OrderDto();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        try {
            List<OrderDetail> orderDetailList = JsonUtil.readValue(orderForm.getItems(),
                    new com.fasterxml.jackson.core.type.TypeReference<List<OrderDetail>>(){});
            orderDTO.setOrderDetailList(orderDetailList);
        }catch (Exception e){
            log.error("对象转换错误，String:{}",orderForm.getItems());
            throw  new SellException(ResultStatus.ORDER_PARAM_ERROR);
        }
        return orderDTO;
    }
}
