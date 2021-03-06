package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Nico
 * 2018/11/16
 *
 * 订单主表的的 Repository 接口
 */
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 根据买家的openid查询订单
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    /**
     * 订单查询根据orderId
     *
     */
    OrderMaster findByOrderId(String orderId);
}
