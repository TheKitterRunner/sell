package com.xinyan.sell.service.impl;

import com.xinyan.sell.dto.CartDto;
import com.xinyan.sell.dto.CartDto;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.dto.OrderDtoTO;
import com.xinyan.sell.enums.OrderStatus;
import com.xinyan.sell.enums.PayStatus;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.repository.ProductRepository;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.service.PayService;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.utils.KeyUtil;
import com.xinyan.sell.webSocket.SellWebSocket;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Nico
 * 2018/11/16
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellWebSocket webSocket;

    @Autowired
    private PayService payService;

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // 生成订单的ID
        String orderId = KeyUtil.generatedUniqueKey();
        // 用来表示订单总金额
        BigDecimal totalAmount = new BigDecimal(0);
        // 订单主表的订单id
        orderDto.setOrderId(orderId);
        // 查询商品
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList){
            ProductInfo productInfo = productRepository.findOne(orderDetail.getProductId());
            if (productInfo == null){
                log.info("创建订单的商品不存在,商品编号为 : " , productInfo.getProductId());
                throw new SellException(ResultStatus.ORDER_NOT_EXIST);
            }

            // 计算订单总额
            totalAmount = totalAmount.add(productInfo.getProductPrice().multiply(
                    new BigDecimal(orderDetail.getProductQuantity())));

            // 订单详情入库
            orderDetail.setDetailId(KeyUtil.generatedUniqueKey());
            orderDetail.setOrderId(orderDto.getOrderId());
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        // 订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(totalAmount);
        orderMaster.setOrderStatus(OrderStatus.NEW_ORDER.getCode());
        orderMaster.setPayStatus(PayStatus.WAIT.getCode());
        // 保存主表信息到数据库
        orderMasterRepository.save(orderMaster);

        // 更新商品的库存
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream().
                map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).
                collect(Collectors.toList());
        productService.decreaseStock(cartDtoList);

        // 发送WebSocket消息
        webSocket.sendMessage(orderDto.getOrderId());

        // 返回数据
        return orderDto;
    }

    /**
     * 查询单个订单(订单详情)
     * @param orderId
     * @return
     */
    @Override
    public OrderDto findOne(String orderId) {
        //如果订单id为空，抛异常
        if (orderId == null){
            throw new SellException(ResultStatus.ORDER_NOT_EXIST);
        }
        //根据orderId查询出orderMaster对象
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultStatus.ORDER_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        //将orderMaster对象的属性复制给orderDto对象
        BeanUtils.copyProperties(orderMaster, orderDto);
        //将查出来的orderDetailList设置进orderDto对象中，并返回
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        orderDto.setOrderDetailList(orderDetailList);

        return orderDto;
    }

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        //分页查询出订单主表
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        //将ordermasterPage对象转为orderDtoPage对象并返回
        Page<OrderDto> orderDtoPage = new PageImpl(orderMasterPage.getContent(),pageable,orderMasterPage.getTotalElements());
        return orderDtoPage;
    }

    /**
     * 取消订单
     * @param orderDto
     * @return
     */
    @Transactional
    @Override
    public OrderDto cancelOrder(OrderDto orderDto) {
        //1.判断订单状态，已完结和已取消订单不能取消
        if (orderDto.getOrderStatus() != OrderStatus.NEW_ORDER.getCode()){
            log.error("新订单无法取消，orderId:{},orderStatus:{}",
                    orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultStatus.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDto.setOrderStatus(OrderStatus.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        //保存到数据库
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if (orderMasterResult == null){
            log.error("订单更新失败，orderMaster:{}",orderMaster);
            throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }
        //3.修改库存(增加)
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("订单中无商品详情,orderDto:{}",orderDto);
            throw new SellException(ResultStatus.ORDER_DETAIL_NOT_EXIST);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream().
                map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).
                collect(Collectors.toList());
        productService.increaseStock(cartDtoList);

        // 4.如果已经支付,需要退款
        if (orderDto.getPayStatus().equals(PayStatus.FINISHED)){
            payService.refund(orderDto);
        }

        return orderDto;
    }

    /**
     * 完结订单
     * @param orderDto
     * @return
     */
    @Transactional
    @Override
    public OrderDto finishOrder(OrderDto orderDto){
        //查询订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatus.NEW_ORDER.getCode())){
            log.error("【完结订单】订单状态不正确，OrderId:{}, OrderStatus:{}"
                    ,orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultStatus.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDto.setOrderStatus(OrderStatus.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster save = orderMasterRepository.save(orderMaster);
        if(save == null){
            log.error("【完结订单】订单更新失败，OrderMaster:{}",orderMaster);
            throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    /**
     * 订单支付完成
     * @param orderDto
     * @return
     */
    @Override
    public OrderDto payOrder(OrderDto orderDto) {
        // 判断订单的状态
        if (!orderDto.getOrderStatus().equals(OrderStatus.NEW_ORDER.getCode())){
            log.error("[订单支付完成] 订单状态不正确, orderId :" + orderDto.getOrderId(),
                    orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultStatus.ORDER_STATUS_ERROR);
        }

        // 判断订单的支付状态
        if (!orderDto.getPayStatus().equals(PayStatus.WAIT.getCode())){
            log.error("[订单支付完成] 订单状态不正确, orderDto :" + orderDto);
            throw new SellException(ResultStatus.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态后重新保存数据
        orderDto.setPayStatus(PayStatus.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        // 如果返回的结果是空,说明订单更新失败
        if (result == null){
            log.error("[订单支付完成] 订单更新失败, orderDto :" +orderDto);
            throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }

        return orderDto;
    }

    /*==================卖家端============*/

    /**
     * 查询所有订单(分页)
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDtoTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDtoTO> orderDTOVOS = new ArrayList<>();

        //此处将支付状态与订单状态替换
        for(OrderMaster orderMaster : orderMasterPage){
            OrderDtoTO orderDtoTO = new OrderDtoTO();
            BeanUtils.copyProperties(orderMaster,orderDtoTO);

            if(orderDtoTO.getOrderId().equals(orderMaster.getOrderId())){
                int orderStatus = orderMaster.getOrderStatus();
                switch (orderStatus){
                    case 0:
                        orderDtoTO.setOrderStatus(OrderStatus.NEW_ORDER.getMessage());
                        break;
                    case 1:
                        orderDtoTO.setOrderStatus(OrderStatus.FINISHED.getMessage());
                        break;
                    case 2:
                        orderDtoTO.setOrderStatus(OrderStatus.CANCEL.getMessage());
                        break;
                }
            }
            if(orderDtoTO.getOrderId().equals(orderMaster.getOrderId())){
                int payStatus = orderMaster.getPayStatus();
                switch (payStatus){
                    case 0:
                        orderDtoTO.setPayStatus(PayStatus.WAIT.getMessage());
                        break;
                    case 1:
                        orderDtoTO.setPayStatus(PayStatus.FINISHED.getMessage());
                        break;
                }
            }
            orderDTOVOS.add(orderDtoTO);
        }
        Page<OrderDtoTO> orderDtoPage = new PageImpl<>(orderDTOVOS, pageable, orderMasterPage.getTotalElements());
        return orderDtoPage;
    }



    /**
     *  完结订单
     * @param orderDto
     * @return
     */
    public OrderDto finish(OrderDto orderDto){
        orderDto.setOrderStatus(OrderStatus.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMasterRepository.save(orderMaster);
        return orderDto;
    }

}
