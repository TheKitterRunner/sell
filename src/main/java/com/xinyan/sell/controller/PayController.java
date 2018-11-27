package com.xinyan.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.rest.type.Get;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Administrator
 * 2018/11/19 0019
 *
 * 微信支付
 */
@Slf4j
@RequestMapping("/pay")
@Controller
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    /**
     * 发起微信支付,创建支付的订单 (弹出确认支付的窗口,就是支付到那里去,总金额,支付方式选择,最后是确认支付的界面)
     * @param orderId
     * @param returnUrl
     * @param map
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map){
        // 1.查询该订单是否存在
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            log.error("[支付订单] 订单不存在 , orderId:{}" +orderId);
            throw new SellException(ResultStatus.ORDER_NOT_EXIST);
        }

        // 2.开始订单支付
        PayResponse payResponse = payService.create(orderDto);

        // 3.返回定制的视图
        // 跳转去create页面
        ModelAndView modelAndView = new ModelAndView("pay/create");
        modelAndView.addObject("payResponse", payResponse);
        modelAndView.addObject("returnUrl", returnUrl);

        return modelAndView;
    }

    /**
     * 微信支付异步通知 ---一致异步通知,知道支付成功,去到success 界面
     * @param notifyData (支付成功需按照指定格式（xml）返回参数给微信支付)
     * @return
     */
    @GetMapping("/notify")
    public String notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        return "pay/success";
    }

}
