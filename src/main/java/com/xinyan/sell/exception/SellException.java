package com.xinyan.sell.exception;

import com.xinyan.sell.enums.ResultStatus;
import lombok.Getter;

/**
 * 3061
 * 2018/11/14
 * 买家异常类
 */
@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultStatus resultStatus) {
//        super(ResultStatus.);
        this.code = code;
    }
}
