package com.xinyan.sell.utils;

import java.util.UUID;

/**
 * 3061
 * 2018/11/14
 * 生成唯一的key
 */
public class KeyUtil {

    /**
     * 生成唯一的key
     * @return
     */
    public static synchronized String generatedUniqueKey(){
        String key = UUID.randomUUID().toString().replace("-", "");
        return key;
    }
}
