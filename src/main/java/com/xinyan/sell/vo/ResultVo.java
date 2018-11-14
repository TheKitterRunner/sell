package com.xinyan.sell.vo;

import lombok.Data;

/**
 * Nico
 * 2018/11/14
 *
 * 返回给客户端的Vo 对象
 */
@Data
public class ResultVo {

    /** 错误码 */
    private Integer code;

    /** 消息 */
    private String msg;

    /** 页面数据 */
    private Object data;
}
