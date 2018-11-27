package com.xinyan.sell.repository;

import com.xinyan.sell.po.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Nico
 * 2018/11/24
 */
public interface SellerRepository extends JpaRepository<SellerInfo, String> {

    /**
     * 通过openid查询卖家的信息
     * @param openid
     * @return
     */
    public SellerInfo findSellerByOpenid(String openid);
}
