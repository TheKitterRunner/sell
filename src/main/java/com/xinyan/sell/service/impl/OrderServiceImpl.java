package com.xinyan.sell.service.impl;

import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.repository.ProductRepository;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 订单业务接口实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        //生成OrderId
        String orderId = KeyUtil.generatedUniqueKey();

        //订单总金额
        BigDecimal orderAmount =  new BigDecimal(BigInteger.ZERO);

        orderDto.setOrderId(orderId);
        //查询商品
        for (OrderDetail orderDetail: orderDto.getOrderDetailList()) {
            //遍历查出每个商品的信息
            ProductInfo productInfo = productRepository.findOne(orderDetail.getProductId());
            if (productInfo == null){
                log.info("创建订单--商品不存在，productId : {}",orderDetail.getOrderId());
                throw new SellException(ResultStatus.PRODUCT_NOT_EXIST);
            }
            //计算订单总金额
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
//            BeanUtils.copyProperties(productInfo,orderDetail);
//            orderDetail.setDetailId(KeyUtil.generatedUniqueKey());
            orderDetail.setOrderId(orderDto.getOrderId());
//            orderDetailRepository.save(orderDetail);

        }
        //订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        //复制orderDto属性到orderMaster
        BeanUtils.copyProperties(orderDto,orderMaster);
//        orderMaster.setOrderId(orderDto.getOrderId());
        orderMaster.setOrderAmount(orderAmount);
        //保存
        orderMasterRepository.save(orderMaster);






        //更新库存
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDto findOne(String orderId) {
        return null;
    }
}
