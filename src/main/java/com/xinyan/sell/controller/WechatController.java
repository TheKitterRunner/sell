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

    /*
    * 第一步：用户同意授权，获取code
    * 第二步：通过code换取网页授权access_token
    * 第三步：刷新access_token（如果需要）
    * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
    */
    /**
     * 返回带 code 和 state 的 url
     * code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，
     *  code只能使用一次，5分钟未被使用自动过期。
     * @param returnUrl
     * @return
     */
    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        // 构造网页授权url,
        String url = "http://227aq28234.imwork.net:36320/sell/wechat/userInfo";

        //oauth2buildAuthorizationUrl 方法，用于返回一个
        //三个参数说明：
        //url：用户授权的url，点击后会重定向并带上 code 和 state 参数
        // scope: 应用授权作用域
        // snsapi_base: 不弹出授权页面，直接跳转，只能获取用户openid
        // snsapi_userinfo: 弹出授权页面，可通过openid拿到昵称、性别、所在地。
        //并且， 即使在未关注的情况下，只要用户授权，也能获取其信息
        //state: 重定向后会带上state参数
        //redirectUrl: 授权后重定向的回调链接地址
        //注意：跳转回调redirect_uri，应当使用https链接来确保授权code的安全性。

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

        return "redirect :" + returnUrl + "?openId=" + openId;
    }
}