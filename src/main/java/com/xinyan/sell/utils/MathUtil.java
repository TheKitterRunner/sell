package com.xinyan.sell.utils;

/**
 * Nico
 * 2018/11/24
 *
 * 数学工具类
 */
public class MathUtil {

    private static final double MONEY_RANGE = 0.01;

    /**
     * 判断两个金额是否相等
     * @param e1
     * @param e2
     * @return
     */
    public static Boolean compareTo(Double e1, Double e2){
        Double result = Math.abs(e1 - e2);
        if (result < MONEY_RANGE) {
            return true;
        }else {
            return false;
        }
    }
}
