package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * 测试订单主表的保存
     */
    @Test
    public void save(){

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName("李四");
        orderMaster.setBuyerAddress("资信达大厦");
        orderMaster.setBuyerOpenid("123456789");
        orderMaster.setBuyerPhone("13587986548");
        orderMaster.setOrderId(KeyUtil.generatedUniqueKey());
        orderMaster.setOrderAmount(new BigDecimal(96));
        orderMasterRepository.save(orderMaster);
    }

    /**
     * 测试通过openid查询订单信息
     */
    @Test
    public void findByBuyerOpenid() {

        String buyerOpenid = "123456789";

        PageRequest pageRequest = new PageRequest(0,2);

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageRequest);
        Assert.assertNotEquals("查询订单", orderMasterPage.getSize());

    }
}