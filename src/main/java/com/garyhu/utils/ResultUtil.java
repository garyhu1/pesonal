package com.garyhu.utils;


import com.garyhu.pojo.Result;

/**
 * Created on 2017/11/7.
 *
 * @author zlf
 * @since 1.0
 */
public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMessage("成功");
        result.setResult(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
}
