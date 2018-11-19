package com.xinyan.sell.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

//    @Test
//    public void createOrder() {
//
//    }
    @Test
    public void cancel(){
        //测试订单状态是否被修改
        OrderMaster orderMaster = orderMasterRepository.findOne("96515284a3074677864a81251d6625cb");
        OrderDto orderDto = new OrderDto();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("96515284a3074677864a81251d6625cb");
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
//        orderDto = orderService.cancelOrder(orderDto);
//        System.out.println(orderDto.getOrderStatus().toString());

        //测试库存是否被修改
        ProductInfo productInfo = productRepository.findOne("3333333333");
        System.out.println(productInfo.getProductStock());
        orderDto = orderService.cancelOrder(orderDto);
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()) {
            productInfo = productRepository.findOne(orderDetail.getProductId());
            System.out.println(productInfo.getProductName()+" : "+productInfo.getProductStock());
        }
    }

}