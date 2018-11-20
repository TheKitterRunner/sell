package com.xinyan.sell.service;

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
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

/**
 * 订单service层的测试
 */
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


    /**
     * 取消订单测试
     */
    @Test
    public void cancel(){
        //测试订单状态是否被修改
        OrderMaster orderMaster = orderMasterRepository.findOne("96515284a3074677864a81251d6625cb");
        OrderDto orderDto = new OrderDto();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("96515284a3074677864a81251d6625cb");
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);

        //测试库存是否被修改
        ProductInfo productInfo = productRepository.findOne("3333333333");
        System.out.println(productInfo.getProductStock());
        orderDto = orderService.cancelOrder(orderDto);
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()) {
            productInfo = productRepository.findOne(orderDetail.getProductId());
            System.out.println(productInfo.getProductName()+" : "+productInfo.getProductStock());
        }
    }

    /**
     * 测试查询单个订单
     */
    @Test
    public void findOne(){
        String orderId = "b8ceaf4812564aef975eccf5da7acb90";

        OrderDto orderDto = orderService.findOne(orderId);

        Assert.assertNotNull("orderDto 不为空", orderDto);
    }

    /**
     * 查询订单列表
     */
    @Test
    public void findList(){
        PageRequest pageRequest = new PageRequest(0,2);

        String openid = "123456789";
        Page<OrderDto> orderMasterPage =orderService.findList(openid, pageRequest);

        Assert.assertNotEquals(0,orderMasterPage.getTotalElements());
    }
}