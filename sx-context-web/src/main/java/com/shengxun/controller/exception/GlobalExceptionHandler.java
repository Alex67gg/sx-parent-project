package com.shengxun.controller.exception;

import com.shengxun.result.JsonResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ASUS on 2018/6/1.
 */
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        return  JsonResult.error();
    }

    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public JsonResult multipartException() {
        return JsonResult.failure("上传文件大小超过限制");
    }
}