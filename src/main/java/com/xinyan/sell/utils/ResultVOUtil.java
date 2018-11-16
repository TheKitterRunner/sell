package com.xinyan.sell.utils;

import com.xinyan.sell.vo.ResultVo;
import lombok.Getter;

@Getter
public class ResultVOUtil {
    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVo success() {
        return success(null);
    }

    /**
     * @param code
     * @param msg
     * @return
     */
    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
