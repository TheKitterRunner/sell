package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表Dao接口
 */
public interface OrderMasterRepository  extends JpaRepository<OrderMaster, String> {
    /**
       * 订单查询：买家 Openid
       * @param buyerOpenid
       * @param pageable
       * @return
       */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
