package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Administrator
 * 2018/11/17
 *
 * 订单详情 Repository 接口
 */
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
        /**
         * 订单查询：买家 Openid
         * @param buyerOpenid
         * @param pageable
         * @return
         */
        Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
