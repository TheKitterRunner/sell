package com.xinyan.sell.repository;

import com.xinyan.sell.enums.OrderStatus;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * OrderMasterRepository接口测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;


    /**
     * 订单主表添加测试
     */
    @Test
    //@Transactional
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(KeyUtil.generatedUniqueKey());
        orderMaster.setBuyerName("李四");
        orderMaster.setBuyerPhone("15367289856");
        orderMaster.setBuyerAddress("资信达大厦401");
        orderMaster.setBuyerOpenid("2143214142");
        orderMaster.setOrderAmount(new BigDecimal("30"));
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);

        orderMasterRepository.save(orderMaster);
    }

    /**
     * 根据openid查询
     */
    @Test
    public void findByBuyerOpenid() {
        String openid = "2143214142";
        PageRequest pageRequest = new PageRequest(0,5);

        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(openid,pageRequest);
    }
}