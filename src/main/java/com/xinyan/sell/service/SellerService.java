package com.xinyan.sell.service;

import com.xinyan.sell.po.SellerInfo;

/**
 * Nico
 * 2018/11/24
 *
 * 卖家端的service接口层
 */
public interface SellerService {

    public SellerInfo findSellerByOpenid(String openid);
}
