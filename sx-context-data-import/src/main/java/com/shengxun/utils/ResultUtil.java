package com.shengxun.utils;


import com.shengxun.domian.Result;

/**
 * Created by chenwei
 * on 2018/1/10 下午4:20.
 */
public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg("系统错误");
        result.setData(msg);
        return result;
    }

    public static Result error(Object object) {
        Result result = new Result();
        result.setCode(500);
        result.setMsg("系统错误");
        result.setData(object);
        return result;
    }

}
