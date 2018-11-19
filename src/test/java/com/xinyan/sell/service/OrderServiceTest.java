package com.xinyan.sell.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.po.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

//    @Test
//    public void createOrder() {
//
//    }
    @Test
    public void findList(){

    }

}