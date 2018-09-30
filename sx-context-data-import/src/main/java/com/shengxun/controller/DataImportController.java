package com.shengxun.controller;

import com.shengxun.domian.Result;
import com.shengxun.service.DataImportService;
import com.shengxun.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by chenwei
 * on 2018/3/20 10:57.
 */
@RequestMapping("/dataImport")
@RestController
public class DataImportController {
    @Autowired
    private DataImportService dataImportService;

    @ApiOperation(value = "查询用户目录", notes = "查询话术目录接口")
    @RequestMapping(value = "/{templateId}/{isSys}", method = {RequestMethod.GET})
    public Result dataImport(@PathVariable(value = "templateId") Long templateId,
                             @PathVariable(value = "isSys") Integer isSys, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (StringUtils.isBlank(userId)) {
            userId = "0";
        }
        dataImportService.dataImport(templateId, userId, isSys);
        return ResultUtil.success();
    }
}
