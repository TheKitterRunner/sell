package com.xinyan.sell.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 与 Java对象转换工具类
 */
public final class JsonUtil {
    private static ObjectMapper objectMapper;

    static {
        if (objectMapper == null){
            objectMapper = new ObjectMapper();
            //ALLOW_UNQUOTED_FIELD_NAMES 允许属性名称没有引号
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        }
    }

    /**
     * Json数组转list
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
        try {
            return objectMapper.readValue(jsonStr,valueTypeRef);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json字符串转换为相应的JavaBean对象
     */
    public static <T> T readValue(String jsonStr,Class<T> valueType){
        try {
            return objectMapper.readValue(jsonStr,valueType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把JavaBean转化为为Json字符串
     */
    public static String toJson(Object object){
        try {
            //格式化输出
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
