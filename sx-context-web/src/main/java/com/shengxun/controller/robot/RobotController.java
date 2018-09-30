package com.shengxun.controller.robot;

import com.shengxun.controller.base.BaseController;
import com.shengxun.utils.SystemUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by ldh on 2018/7/4.
 * 文字试用Controller
 */
@Api(value = "文字试用Controller", tags = {"文字试用接口"})
@Controller
public class RobotController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(RobotController.class);

    @Value("${server.port}")
    private String port;

@RequestMapping(value = "/index")
    public String index(ModelMap map)throws Exception{
        String localIp = "";
        localIp = SystemUtil.getServerIp();
        map.addAttribute("ipAndPort", localIp +":"+port);
        return "index";
    }
}