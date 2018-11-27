package com.xinyan.sell.exception;

import com.xinyan.sell.config.ProjectUrlConfig;
import com.xinyan.sell.utils.ResultVOUtil;
import com.xinyan.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 拦截身份认证异常处理
     * @return
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public String handlerAuthorizeException() {
        return "redirect:".concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login");
    }

    /**
     * 系统异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = SellException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN) //响应头状态码返回特定状态码
    @ResponseBody
    public ResultVo handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
