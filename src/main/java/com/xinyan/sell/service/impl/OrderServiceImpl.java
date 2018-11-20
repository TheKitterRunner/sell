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
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        return orderDto;
    }

    /**
     * 完结订单
     * @param orderDto
     * @return
     */
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
}
