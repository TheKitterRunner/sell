package com.xinyan.sell.enums;

import lombok.Getter;

/**
 * 3061
 * 2018/11/14
 * 支付状态的枚举类
 */
@Getter
public enum PayStatus {

    WAIT(0, "未支付"),
    FINISHED(1, "支付完成")
    ;

    private Integer code;
    private String message;

    PayStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
