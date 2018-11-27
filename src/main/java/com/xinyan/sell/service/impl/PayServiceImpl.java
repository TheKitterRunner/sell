package com.xinyan.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.service.PayService;
import com.xinyan.sell.utils.JsonUtil;
import com.xinyan.sell.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Nico
 * 2018/11/24
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    /**
     * 发起订单支付
     * @param orderDto
     * @return
     */
    @Override
    public PayResponse create(OrderDto orderDto) {
        // 向支付请求中添加相应的属性值
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】发起支付, request={}", JsonUtil.toJson(payRequest));
        // 发起支付
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付, response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    /**
     * 微信支付异步通知
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        // 1.验证签名 SDK已经实现
        // 2.支付状态 SDK已经实现
        // 3.支付金额的确认
        // 4.支付的用户验证(可以自己支付,也可以朋友代付)
        // 返回一个XML格式的字符串
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知, payResponse:{}", JsonUtil.toJson(payResponse));

        // 查询该订单是否存在
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        if (orderDto == null){
            log.error("[微信支付] 异步通知, 订单不存在 , orderId:{}" , payResponse.getOrderId());
            throw new SellException(ResultStatus.ORDER_NOT_EXIST);
        }

        // 判断订单金额和支付金额是否一致
        if (!MathUtil.compareTo(payResponse.getOrderAmount(), orderDto.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 订单总金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDto.getOrderAmount());
            throw new SellException(ResultStatus.WECHAT_MP_PAY_NOTIFY_MONEY_ERROR);
        }

        // 如果相同就修改订单支付状态
        orderService.payOrder(orderDto);

        // 返回支付结果相应
        return payResponse;
    }

    /**
     * 微信退款
     * @param orderDto
     * @return
     */
    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request : {}",JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款] request : {}",JsonUtil.toJson(refundRequest));
        return refundResponse;
    }
}
