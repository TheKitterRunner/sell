package com.xinyan.sell.controller;

import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Nico
 * 2018/11/17
 *
 * 获取微信端授权的Controller
 */
@RequestMapping("/wechat")
@Slf4j
@Controller
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 返回带 code 和 state 的 Url
     * @param returnUrl
     * @return
     */
    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        // 构造网页授权url,
        String url = "http://227aq28234.imwork.net:35419/sell/wechat/userInfo";

        String redirectUrl = null;
        try {
            redirectUrl = wxMpService.oauth2buildAuthorizationUrl(
                    url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl, "utf-8"));
        }catch (UnsupportedEncodingException e){
            log.error("[微信网页授权]获取code, redirectUrl:{}", redirectUrl);
            throw new SellException(ResultStatus.WECHAT_MP_AUTHORIZE_ERROR);
        }

        return "redirect : " + redirectUrl;
    }

    /**
     * 返回带openid的Url
     * openid : 是微信用户在商户端对应的APPId下的唯一标识
     * @param returnUrl
     * @param code
     * @return
     */
    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl){

        WxMpUser wxMpUser = null;

        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken =
                    wxMpService.oauth2getAccessToken(code);
            // 获取用户基本信息
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,null);
        } catch (WxErrorException e){
            log.error("[微信网页授权]{}" , e);
            throw new SellException(ResultStatus.WECHAT_MP_AUTHORIZE_ERROR.getCode(),
                    e.getError().getErrorMsg());
        }

        String openId = wxMpUser.getOpenId();

        return "redirect :" + returnUrl + "?openId" + openId;
    }
}
