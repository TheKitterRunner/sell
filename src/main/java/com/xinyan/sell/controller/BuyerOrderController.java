package com.xinyan.sell.controller;

import com.xinyan.sell.converter.OrderMasterToOrderDTOConverter;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.utils.ResultVOUtil;
import com.xinyan.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private OrderMasterRepository orderMasterRepository;



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

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("orderId") String orderId){
        OrderMaster master = orderMasterRepository.findByOrderId(orderId);
        OrderDto orderDto =OrderMasterToOrderDTOConverter.converter(master);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        orderDto.setOrderDetailList(orderDetails);
        orderService.cancelOrder(orderDto);
        return ResultVOUtil.success(null);
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVo finish(@RequestParam("orderId") String orderId){
        OrderMaster master = orderMasterRepository.findByOrderId(orderId);
        OrderDto orderDto =OrderMasterToOrderDTOConverter.converter(master);
        OrderDto finish = orderService.finish(orderDto);
        return ResultVOUtil.success(finish);

    }

}
