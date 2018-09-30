package com.shengxun.handle;

import com.shengxun.domian.Result;
import com.shengxun.execption.DataImportException;
import com.shengxun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by chenwei
 * on 2018/1/10 下午4:17.
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        LOGGER.error("[系统异常]", e);
        if (e instanceof DataImportException) {
            DataImportException synonymException = (DataImportException) e;
            return ResultUtil.error(synonymException.getCode(), synonymException.getMessage());
        } else {
            return ResultUtil.error(e.getMessage());
        }
    }
}
