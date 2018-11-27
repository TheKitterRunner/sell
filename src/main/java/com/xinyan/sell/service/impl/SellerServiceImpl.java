package com.xinyan.sell.service.impl;

import com.xinyan.sell.po.SellerInfo;
import com.xinyan.sell.repository.SellerRepository;
import com.xinyan.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Nico
 * 2018/11/24
 * 卖家服务层的实现类
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    /**
     * 根据openid查询信息
     * @param openid
     * @return
     */
    @Override
    public SellerInfo findSellerByOpenid(String openid) {
        return sellerRepository.findSellerByOpenid(openid);
    }
}
