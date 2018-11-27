package com.xinyan.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Nico
 * 2018/11/23
 */
@Component
public class WeChatOpenConfig {

    @Autowired
    private WxMpService wxMpService;


}
