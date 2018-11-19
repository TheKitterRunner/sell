package com.xinyan.sell.service.impl;

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
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
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
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);

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
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(),pageable.getPageSize());
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        Page<OrderDto> orderDtoPage = new PageImpl(orderMasterPage.getContent(), pageable, orderMasterPage.getTotalElements());
        return orderDtoPage;
    }

    @Override
    public OrderDto cancelOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto finishOrder(OrderDto orderDto) {
        return null;
    }
}
