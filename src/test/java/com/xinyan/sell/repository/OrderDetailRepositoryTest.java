package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void save(){
        String orderid = KeyUtil.generatedUniqueKey();

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName("张三丰");
        orderMaster.setBuyerAddress("金源商务大厦");
        orderMaster.setBuyerOpenid("555555555");
        orderMaster.setBuyerPhone("13987216548");
        orderMaster.setOrderId(orderid);
        orderMaster.setOrderAmount(new BigDecimal(96));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(KeyUtil.generatedUniqueKey());
        orderDetail.setOrderId(orderid);
        orderDetail.setProductId("3645e800598f4911a60788399c9ea238");
        orderDetail.setProductName("臊子面");
        orderDetail.setProductPrice(new BigDecimal(20));
        orderDetail.setProductQuantity(3);
        orderDetail.setProductIcon("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2591780415,1191633011&fm=26&gp=0.jpg");

        orderDetailRepository.save(orderDetail);
        orderMasterRepository.save(orderMaster);
    }
    /**
     * 通过orderID查找订单详情
     */
    @Test
    public void findByOrderId() {


    }
}