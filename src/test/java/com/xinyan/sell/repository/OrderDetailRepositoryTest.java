package com.xinyan.sell.repository;


import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * OrderDetailRepository接口单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * OrderDetail添加测试
     */
    @Test
    public void save(){
        //添加订单主表信息
        String orderid = KeyUtil.generatedUniqueKey();
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderid);
        orderMaster.setBuyerName("李四");
        orderMaster.setBuyerPhone("15367289856");
        orderMaster.setBuyerAddress("资信达大厦401");
        orderMaster.setBuyerOpenid("2143214142");
        orderMaster.setOrderAmount(new BigDecimal("30"));
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);

        //添加订单详情信息
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(KeyUtil.generatedUniqueKey());
        orderDetail.setOrderId(orderid);
        orderDetail.setProductName("香菇炖鸡");
        orderDetail.setProductPrice(new BigDecimal("25"));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542382402140&di=1ac2fbfea51698960434a0e277b50309&imgtype=0&src=http%3A%2F%2Fcp1.douguo.com%2Fupload%2Fcaiku%2F8%2F9%2Fc%2Fyuan_8992296c40cfe860fcccc51da9877d1c.jpg");

        orderMasterRepository.save(orderMaster);
        //orderDetailRepository.save(orderDetail);

        //Assert.assertNotEquals("保存订单详情",orderDetailResult);
    }

    @Test
    public void findByOrderId() {
    }
}