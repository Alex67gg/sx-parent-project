package com.shengxun.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shengxun.controller.base.BaseController;
import com.shengxun.entity.User;
import com.shengxun.enums.UserTemplateStatus;
import com.shengxun.result.JsonResult;
import com.shengxun.result.vo.SearchInfo;
import com.shengxun.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * create by qq on 2018/6/12
 */
@Api(value = "用户controller", tags = {"用户接口"})
@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    private static Logger logger = Logger.getLogger("UserController");

    /**
     * 搜索用户信息
     *
     * @Return 用户信息
     */
    @ApiOperation(value = "搜索用户信息", notes = "搜索用户信息")
    @PostMapping(value = "/getUserInfoByCondition")
    public JsonResult selectUserByCondition(@RequestBody SearchInfo searchInfo) {
        /**
         * 判断传递页面参数是否为空,
         * 若为空则设置默认pageSize=10;pageNum=1
         */
        if (searchInfo.getPageSize() == null) {
            searchInfo.setPageSize(10);
        }
        if (searchInfo.getPageNum() == null) {
            searchInfo.setPageNum(1);
        }
        PageHelper.startPage(searchInfo.getPageNum(), searchInfo.getPageSize());
        List<User> users = userService.selectUserByCondition(searchInfo.getSearchName(),
                searchInfo.getPageSize(),
                searchInfo.getPageNum());
        if (CollectionUtils.isEmpty(users)) {
            return JsonResult.success("查无此用户");
        }
        if (searchInfo.getStatus() != null) {
            List<User> list = userService.selectByStatus(searchInfo.getStatus(), users);
            if (CollectionUtils.isEmpty(list)){
                return JsonResult.success("查无此用户");
            }
            PageInfo pageInfo = new PageInfo(list, searchInfo.getPageSize());
            return JsonResult.success("查询成功", pageInfo);
        }
        PageInfo pageInfo = new PageInfo(users, searchInfo.getPageSize());
        return JsonResult.success("查询成功", pageInfo);
    }

    /**
     * 修改用户模板
     *
     * @Return 用户信息
     */
    @ApiOperation(value = "模板状态修改", notes = "模板状态修改")
    @PutMapping(value = "/updateTemplateStatus/{template_id}/{status}")
    public JsonResult updateTemplateStatus(@PathVariable(value = "template_id") Long template_id,
                                           @PathVariable(value = "status") Integer status) {

        if (template_id == null || status == null) {
            return JsonResult.failure("参数有误");
        } else if (!UserController.isInClude(status)) {
            return JsonResult.failure("参数有误");
        }
        Integer result = userService.updateTemplateStatus(template_id, status);
        if (result < 1) {
            return JsonResult.failure("修改失败");
        }
        return JsonResult.success("修改成功", result);
    }

    /**
     * @param cooperative_partner
     * @return 用户id ,以及企业名称
     */
    @ApiOperation(value = "查询企业名称", notes = "查询企业全称")
    @GetMapping(value = "selectCompanyName")
    public JsonResult selectCompanyName(String cooperative_partner) {
        List<User> list = userService.selectCompanyName(cooperative_partner);
        if (list.size() != 0) {
            return JsonResult.success("查询成功", list);
        }
        return JsonResult.success("查无此用户");
    }

    public static boolean isInClude(Integer status) {
        boolean inClude = false;
        for (UserTemplateStatus s : UserTemplateStatus.values()) {
            if (s.getValue() == status) {
                inClude = true;
                break;
            }
        }
        return inClude;
    }
}