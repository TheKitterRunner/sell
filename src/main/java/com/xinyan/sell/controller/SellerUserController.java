package com.xinyan.sell.controller;

import com.lly835.bestpay.rest.type.Get;
import com.xinyan.sell.config.ProjectUrlConfig;
import com.xinyan.sell.constant.CookieConstant;
import com.xinyan.sell.constant.RedisConstant;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.po.SellerInfo;
import com.xinyan.sell.service.SellerService;
import com.xinyan.sell.utils.CookieUtil;
import com.xinyan.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Nico
 * 2018/11/24
 *
 * 卖家端用户的Controller
 */
@Slf4j
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 卖家登录界面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "login";
    }

    /**
     * 账户密码登录
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/accountLogin")
    public String accountLogin(@RequestParam("account") String account,
                               @RequestParam("password") String password) {
        return "redirect:/seller/order/list";
    }
    /**
     * 微信登录
     * @param openid
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        // 1.根据openid查询卖家信息
        SellerInfo sellerByOpenid = sellerService.findSellerByOpenid(openid);
        if (sellerByOpenid == null){
            map.put("msg", ResultStatus.LOGIN_FAILED.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        // 2.将 Token 设置到redis中
        String token = String.format(RedisConstant.TOKEN_PREFIX, KeyUtil.generatedUniqueKey());

        stringRedisTemplate.opsForValue().set(token, openid, RedisConstant.EXPIRE, TimeUnit.SECONDS);

        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return new ModelAndView("redirect:/seller/order/list");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultStatus.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
