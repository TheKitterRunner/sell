package com.xinyan.sell.service.impl;

import com.sun.xml.internal.bind.v2.TODO;
import com.xinyan.sell.dto.CartDto;
import com.xinyan.sell.dto.OrderDto;
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
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.Collection;
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

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
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
            totalAmount.add(productInfo.getProductPrice().multiply(
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

        // 返回数据
        return orderDto;
    }

    /**
     * 根据orderId查询订单详情(单个订单)
     * @param orderId
     * @return
     */
    @Override
    public OrderDto findOne(String orderId) {
        //根据orderId查询订单主表
        OrderMaster byOrderId = orderMasterRepository.findByOrderId(orderId);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(byOrderId,orderDto);
        //根据orderId查询订单详情表
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        //把每一个订单详情表写进orderDTO中
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }


    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    /**
     * 取消订单
     * @param orderDto
     * @return
     */
    @Override
    public OrderDto cancelOrder(OrderDto orderDto) {
        //查询订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatus.NEW_ORDER.getCode())){
           log.error("【取消订单】订单状态不正确，OrderId:{}, OrderStatus:{}",
                     orderDto.getOrderId(),orderDto.getOrderStatus());
        }
        //修改支付状态
        orderDto.setOrderStatus(OrderStatus.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateMaster = orderMasterRepository.save(orderMaster);
        if(updateMaster == null){
           log.error("【取消订单】订单更新失败，OrderMaster: {}",orderMaster);
           throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，OrderDto:{}",orderDto);
            throw new SellException(ResultStatus.ORDER_DETAIL_NOT_EXIST);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e ->new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDtoList);

        //如果已经支付需要退款
        return orderDto;

    }

    /**
     * 完结订单
     * @param orderDto
     * @return
     */
    @Override
    public OrderDto finish(OrderDto orderDto){
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
}
