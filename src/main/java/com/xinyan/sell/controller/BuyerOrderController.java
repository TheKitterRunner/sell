package com.xinyan.sell.controller;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.utils.ResultVOUtil;
import com.xinyan.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nico
 * 2018/11/17
 */
@RequestMapping("/buyer/order")
@Slf4j
@RestController
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVo details(@Param("orderId") String orderId){
        if(orderId == null){
            log.error("【查询订单详情失败】订单详情不存在");
            throw new SellException(ResultStatus.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDto orderDto = orderService.findOne(orderId);
        return ResultVOUtil.success(orderDto);
    }
}
