package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.repository.OrderMasterRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrder() {
//        OrderDto orderDto = new OrderDto();
//        orderDto.setBuyerName("李四");
//        orderDto.setBuyerAddress("资信达大厦");
//        orderDto.setBuyerOpenid("11234455");
//        orderDto.setBuyerPhone("12345454787");
//        List<OrderDetail> orderDetailList = new ArrayList<>();

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