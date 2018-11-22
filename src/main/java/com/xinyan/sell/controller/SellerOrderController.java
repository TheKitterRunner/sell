package com.xinyan.sell.controller;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.dto.OrderDtoTO;
import com.xinyan.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@RequestMapping("/seller/order")
@Controller
public class SellerOrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 订单列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                       @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                       Map<String, Object> map){
        PageRequest pageRequest = new PageRequest(page - 1, size);

        Page<OrderDtoTO> orderDtoPage = orderService.findList(pageRequest);

        map.put("orderDtoPage",orderDtoPage);

        return "order/list";
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam("orderId") String orderId,
                         Map<String, Object> map){
        OrderDto orderDto = orderService.findOne(orderId);
        map.put("orderDto", orderDto);

        return "order/detail";
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @GetMapping("/finish")
    public String finish(@RequestParam("orderId") String orderId){
        OrderDto orderDto = orderService.findOne(orderId);
        orderService.finish(orderDto);
        return "redirect:list";
    }


    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public String updateOrderMasterStatusByIdCancel(@RequestParam("orderId") String orderId){
        OrderDto orderDto = orderService.findOne(orderId);
        orderService.cancelOrder(orderDto);
        return "redirect:list";
    }

}
