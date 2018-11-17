package com.xinyan.sell.controller;

import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Nico
 * 2018/11/17
 */
@RequestMapping("/buyer/order")
@Controller
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


}
