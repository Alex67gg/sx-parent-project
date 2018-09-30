package com.shengxun.domian;

import lombok.Data;

/**
 * Created by chenwei
 * on 2018/1/10 下午3:59.
 */
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */

    private String msg;

    /**
     * 具体的内容
     */
    private T data;
}
