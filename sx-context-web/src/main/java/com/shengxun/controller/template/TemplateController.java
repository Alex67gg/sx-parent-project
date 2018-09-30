package com.shengxun.controller.template;

import com.shengxun.controller.base.BaseController;
import com.shengxun.entity.*;
import com.shengxun.enums.Comm;
import com.shengxun.enums.TemplateStatus;
import com.shengxun.enums.UserTemplateStatus;
import com.shengxun.result.JsonResult;
import com.shengxun.result.vo.Template;
import com.shengxun.result.vo.TemplateVo;
import com.shengxun.service.*;
import com.shengxun.utils.GeneratorVideoVame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ASUS on 2018/6/7.
 * 话术模板
 */
@Api(value="话术模板controller",tags={"话术模板接口"})
@RestController
public class TemplateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateAllService templateAllService;

    @Autowired
    private PlateService plateService;

    @Autowired
    private GeneratorVideoVame generatorVideoVame;

    @Autowired
    private TemplateUserService templateUserService;

    @Autowired
    private IndustryUserService industryUserService;

    @Autowired
    private IndustryAllService industryAllService;
    /**
     * 复制
     * @param templateIds 原模板ID
     * @param industryId  行业ID
     * @return
     */
    @ApiOperation(value="复制话术模板",notes = "模板复制")
    @PostMapping(value = "/template/copy/{templateIds}/{industryId}/{type}")
    public JsonResult copy(
            @ApiParam(value = "模板Ids",type = "String",required = true) @PathVariable(value = "templateIds") String templateIds ,
            @ApiParam(value = "行业Id",type = "Long",required = true) @PathVariable(value = "industryId") Long industryId,
            @ApiParam(value =
                    "0为系统复制到系统" +
                    "1为系统复制到用户" +
                    "2为用户复制到用户",type = "Integer",required = true) @PathVariable(value = "type") Integer type) {
       //验证参数
        if (StringUtils.isBlank(templateIds) || industryId == null || type == null){
            return JsonResult.failure("参数有误");
        }
        String[] temps = templateIds.split(",");
        if (temps.length < 1){
            return JsonResult.failure("参数有误");
        }
        if (type ==0){//0为系统复制到系统
            IndustryAll industryAll = industryAllService.selectByPrimaryKey(industryId.intValue());
            if (industryAll == null){
                return JsonResult.success("当前行业不存在，或者已被删除");
            }
            for (String templateId: temps) {
                TemplateAll stq =null;
                if (StringUtils.isNoneBlank(templateId)){
                    Long temp =  Long.parseLong(templateId);
                    stq = templateAllService.selectFullByPrimaryKey( temp);
                }
                if (stq !=null){
                    stq.setIndustryAllId(industryId);
                    stq.setName(stq.getName());
                    stq.setStatus(TemplateStatus.getValue(TemplateStatus.usable));
                    Integer isSys = 0;
                    templateAllService.insert(stq);
                    plateService.InsertPlates(stq.getPlates(),isSys,stq.getId(),new AtomicInteger(0),stq.getName());
                }
            }
            return JsonResult.success("复制成功");
        } if (type ==1){//1为系统复制到用户
            IndustryUser industryUser = industryUserService.selectByPrimaryKey(industryId);
            if (industryUser == null){
                return JsonResult.success("用户当前文件夹不存在，或者已被删除有误");
            }
            for (String templateId: temps) {
                TemplateAll stq =null;
                if (StringUtils.isNoneBlank(templateId)){
                    Long temp =  Long.parseLong(templateId);
                    stq = templateAllService.selectFullByPrimaryKey( temp);
                }
                if (stq !=null){
                    TemplateUser  templateUser = new TemplateUser();
                    templateUser.setIndustryUserId(industryId);
                    templateUser.setStatus(UserTemplateStatus.getValue(UserTemplateStatus.TOAUDIT));
                    templateUser.setName(stq.getName());
                    Integer isSys = 1;
                    templateUser.setPlates(stq.getPlates());
                    templateUserService.insert(templateUser);
                    plateService.InsertPlates(templateUser.getPlates(),isSys,templateUser.getId(),new AtomicInteger(0),stq.getName());
                }
            }
            return JsonResult.success("复制成功");
        } if (type ==2){
            IndustryUser industryUser = industryUserService.selectByPrimaryKey(industryId);
            if (industryUser == null){
                return JsonResult.success("用户当前文件夹不存在，或者已被删除有误");
            }
            for (String templateId: temps) {
                TemplateUser stq =null;
                if (StringUtils.isNoneBlank(templateId)){
                    Long temp =  Long.parseLong(templateId);
                    stq = templateUserService.selectFullByPrimaryKey( temp);
                }
                if (stq !=null){
                    stq.setIndustryUserId(industryId);
                    stq.setStatus(UserTemplateStatus.getValue(UserTemplateStatus.TOAUDIT));
                    stq.setName(stq.getName());
                    Integer isSys = 1;
                    stq.setPlates(stq.getPlates());
                    templateUserService.insert(stq);
                    plateService.InsertPlates(stq.getPlates(),isSys,stq.getId(),new AtomicInteger(0),stq.getName());
                }
            }
            return JsonResult.success("复制成功");
        }
        return JsonResult.failure("type参数有误");
    }

    /**
     * 查询整个模板
     * @param templateId  原模板ID
     * @return
     */
    @ApiOperation(value="查询整个模板",notes = "查询整个模板接口")
    @GetMapping(value = "/template/{templateId}/{type}")
    public JsonResult getFull(
            @ApiParam(value = "模板Id",type = "Long",required = true) @PathVariable(value = "templateId") Long templateId,
            @ApiParam(value = "类型 0为全局行业，1位用户行业",type = "Integer",required = true) @PathVariable(value = "type") Integer type) throws Exception{

        if (templateId == null || type == null){
            return JsonResult.failure("参数有误");
        }
        Template template = new Template();
        if (type ==0){
            TemplateAll stq = templateAllService.selectFullByPrimaryKey(templateId);
            if (stq == null){
                return JsonResult.success("模板不存在或者已被删除");
            }
            template.setIndustryId(stq.getIndustryAllId());
            template.setId(stq.getId());
            template.setName(stq.getName());
            template.setPlates(stq.getPlates());
            return JsonResult.success(template);
        }if (type ==1){
            TemplateUser templateUser = templateUserService.selectFullByPrimaryKey(templateId);
            if (templateUser == null){
                return JsonResult.success("模板不存在或者已被删除");
            }
            template.setIndustryId(templateUser.getIndustryUserId());
            template.setId(templateUser.getId());
            template.setName(templateUser.getName());
            template.setPlates(templateUser.getPlates());
            return JsonResult.success(template);
        }
        return JsonResult.failure("type参数有误");
    }

    /**
     * 新建模板
     * @param industryId
     * @param name
     * @return
     */
    @ApiOperation(value = "新建模板",notes = "新建模板")
    @PostMapping(value = "/template/{industryId}/{name}/{type}")
    public JsonResult add(@ApiParam(value = "行业Id",type = "Long",required = true) @PathVariable(value = "industryId") Long industryId,
                                 @ApiParam(value = "模板名称（限制为50个字符）",type = "String",required = true)@PathVariable(value = "name") String name,
                                 @ApiParam(value = "类型 0为全局行业，1位用户行业",type = "Integer",required = true) @PathVariable(value = "type") Integer type){
        //验证参数
        if (type == null || industryId == null ||  StringUtils.isEmpty(name) || name.length() >50){
            return JsonResult.failure("参数有误");
        }
        if (type ==0){
            IndustryAll industryAll = industryAllService.selectByPrimaryKey(industryId.intValue());
            if (industryAll == null){
                return JsonResult.success("当前行业不存在，或者已被删除");
            }
            TemplateAll qjmb = new TemplateAll();
            qjmb.setStatus(Comm.KE_YONG);
            qjmb.setName(name);
            qjmb.setIndustryAllId(industryId);
            templateAllService.insertQJMB(qjmb);
            return JsonResult.success("添加成功");
        }if (type ==1){
            IndustryUser industryUser = industryUserService.selectByPrimaryKey(industryId);
            if (industryUser == null){
                return JsonResult.success("用户当前文件夹不存在，或者已被删除有误");
            }
            TemplateUser templateUser = new TemplateUser();
            templateUser.setName(name);
            templateUser.setIndustryUserId(industryId);
            templateUser.setStatus(UserTemplateStatus.getValue(UserTemplateStatus.TOAUDIT));
            int result = templateUserService.insert(templateUser);
            /*if (result > 0){*/
                return JsonResult.success("添加成功");
           /* }
            return JsonResult.failure("添加失败");*/
        }
        return JsonResult.failure("type参数有误");
    }

    @ApiOperation(value = "模板重命名",notes="模板重命名接口")
    @PutMapping(value = "/template/{templateId}/{name}/{type}")
    public JsonResult update(@ApiParam(value = "要修改的模板templateId",type = "Long",required = true)@PathVariable(value = "templateId") Long templateId,
                                 @ApiParam(value = "新的名称（限制为50个字符）",type = "String",required = true)   @PathVariable(value = "name") String name,
                                 @ApiParam(value = "类型 0为全局行业，1位用户行业",type = "Integer",required = true) @PathVariable(value = "type") Integer type){
        //验证参数
        if (type == null || templateId == null ||  StringUtils.isEmpty(name) || name.length() >50){
            return JsonResult.failure("参数有误");
        }
        if (type ==0){
           TemplateAll templateAll =  templateAllService.selectByPrimaryKey(templateId);
            if (templateAll == null){
                return JsonResult.success("无此模板");
            }
                templateAllService.updateNameById(templateId,name);
                return JsonResult.success("修改成功");
        }if (type ==1){
            TemplateUser templateUser = templateUserService.selectByPrimaryKey(templateId);
            if (templateUser == null){
                return JsonResult.success("无此模板");
            }
            templateUser.setName(name);
            int result = templateUserService.updateByPrimaryKey(templateUser);
            /*if (result > 0){*/
                return JsonResult.success("修改成功");
           /* }
            return JsonResult.failure("修改失败");*/
        }
        return JsonResult.failure("type参数有误");
    }

    /**
     * 实时保存系统模板
     */
    @ApiOperation(value="实时保存系统模板",notes = "实时保存系统模板接口")
    @PutMapping(value = "/template/currentTime/save")
    public JsonResult currentTimeSave(@RequestBody TemplateVo templateVo) {
        Integer type = templateVo.getType();
        Long userId = templateVo.getUserId();
        Template template = templateVo.getTemplate();
        //验证参数
        if (type == null || template == null || template.getId() == null || StringUtils.isEmpty(template.getName())){
            return JsonResult.failure("参数有误");
        }
        Long  templateId = template.getId();
        //准备参数
        AtomicInteger ai = new AtomicInteger(0);
        String videoName ="";
        String name = template.getName();
        try {
            videoName = generatorVideoVame.getVideoVame(name);
            System.out.print("videoName"+videoName);
        }catch (Exception e){
            videoName = "DEMO";
        }
        if (type == 0) {
            TemplateAll templateAll = templateAllService.selectByPrimaryKey(templateId);
            if (templateAll == null){
                return JsonResult.success("模板不存在，或已被删除");
            }
            List<Plate> plates = template.getPlates();
            if (!CollectionUtils.isEmpty(plates)){
                List<Plate> plateList =  plateService.updateOrInsertPlates(template.getPlates(),type,templateId,ai,videoName);
                template.setPlates(plateList);
                return JsonResult.success("更新成功",template);
            }else {
                return JsonResult.failure("更新失败");
            }
        }if (type == 1){
            if (userId == null){
                return JsonResult.failure("参数有误");
            }
            TemplateUser templateUser = templateUserService.selectByPrimaryKey(templateId);
            if (templateUser == null){
                return JsonResult.success("模板不存在，或已被删除");
            }
            List<Plate> plates = template.getPlates();
            if (!CollectionUtils.isEmpty(plates)){
                List<Plate> plateList =  plateService.updateOrInsertPlates(template.getPlates(),type,templateId,ai,videoName);
                template.setPlates(plateList);
                return JsonResult.success("更新成功",template);
            }else {
                return JsonResult.failure("更新成功");
            }
        }
        return JsonResult.failure("type参数有误");
    }

    /**
     * 删除话术模板
     * @param templateId 模板id
     * @return 状态
     */
    @ApiOperation(value="删除话术模板",notes="删除话术模板")
    @DeleteMapping(value="/template/{templateId}/{type}")
    public JsonResult del(@ApiParam(value = "模板id",type = "Long",required = true) @PathVariable(value = "templateId") Long templateId,
                          @ApiParam(value = "类型 0为全局行业，1位用户行业",type = "Integer",required = true) @PathVariable(value = "type") Integer type){
        if (templateId == null || type == null) {
            return JsonResult.failure("参数有误");
        }
        if (type == 0){
            TemplateAll templateAll = templateAllService.selectByPrimaryKey(templateId);
            if (templateAll != null){
                templateAll.setStatus(TemplateStatus.getValue(TemplateStatus.del));
                int result = templateAllService.updateByPrimaryKey(templateAll);
                if (result > 0 ){
                    return JsonResult.success("删除成功");
                }
                return JsonResult.success("删除失败");
            }
            return JsonResult.success("模板不存在或已被删除");
        }
        else if (type == 1){
            TemplateUser templateUser = templateUserService.selectByPrimaryKey(templateId);
            if (templateUser!=null){
                templateUser.setStatus(UserTemplateStatus.getValue(UserTemplateStatus.DEL));
                int result = templateUserService.updateByPrimaryKey(templateUser);
                /*if (result > 0){*/
                    return JsonResult.success("删除成功");
                /*}
                return JsonResult.success("删除失败");*/
            }
            return JsonResult.success("模板不存在或已被删除");
        }else {
            return JsonResult.failure("type参数有误");
        }
    }
}