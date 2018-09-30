package com.shengxun.controller.plate;

import com.shengxun.controller.base.BaseController;
import com.shengxun.entity.*;
import com.shengxun.result.JsonResult;
import com.shengxun.result.vo.SaveFile;
import com.shengxun.result.vo.SaveFiles;
import com.shengxun.service.DialogueService;
import com.shengxun.service.TemplateUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldh on 2018/6/12.
 * 板块控制层
 */
@Api(value="板块controller",tags={"板块接口"})
@RestController
public class PlateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PlateController.class);

    @Autowired
    private com.shengxun.service.PlateService PlateService;

    @Autowired
    private com.shengxun.service.TemplateAllService TemplateAllService;

    @Autowired
    private TemplateUserService templateUserService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private com.shengxun.service.ContextService ContextService;

    /**
     * 删除板块
     * @param plateId 板块
     */
    @ApiOperation(value="删除板块",notes="删除板块")
    @DeleteMapping(value="/plate/{plateId}")
    public JsonResult delPlate(@ApiParam(value = "板块Id",type = "Long",required = true)@PathVariable(value = "plateId")  Long plateId){
        if (plateId == null){
            return JsonResult.failure("参数有误");
        }
        if (PlateService.selectByPrimaryKey(plateId) == null){
            return JsonResult.failure("板块信息不存在");
        }
        int result = PlateService.deleteByPrimaryKey(plateId);
        if (result > 0){
            return JsonResult.success("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    /**
     * 根据话术模板ID查询所有板块
     * @param tempId 板块
     */
    @ApiOperation(value="查询模板下的板块",notes="修改板块")
    @GetMapping(value="/plate/{tempId}")
    public JsonResult getList(@ApiParam(value = "模板ID",type = "Long",required = true)@PathVariable(value = "tempId") Long tempId){
        if (tempId == null){
            return JsonResult.failure("参数有误");
        }
        List<Plate> Plates =  PlateService.selectListByTempId(tempId);
        if (CollectionUtils.isEmpty(Plates)){
            return JsonResult.failure("暂无板块");
        }
        return JsonResult.success(Plates);
    }

    /**
     * 删除语境
     * @param contextId 语境
     */
    @ApiOperation(value="删除语境",notes="删除语境")
    @DeleteMapping(value="/plate/context/{contextId}")
    public JsonResult delContext(@ApiParam(value = "板块Id",type = "Long",required = true)@PathVariable(value = "contextId") Long contextId){
        if (contextId == null){
            return JsonResult.failure("参数有误");
        }
        if (ContextService.selectByPrimaryKey(contextId) == null){
            return JsonResult.failure("语境信息有误");
        }
        int result = ContextService.deleteByPrimaryKey(contextId);
        if (result > 0){
            return JsonResult.success("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    /**
     * 根据板块D查询所有一级语境
     * @param plateId 板块
     */
    @ApiOperation(value="查询板块下的一级语境",notes="查询板块下的一级语境接口")
    @GetMapping(value="/plate/contextList/{plateId}")
    public JsonResult getContextList(@ApiParam(value = "板块Id",type = "Long",required = true)@PathVariable(value = "plateId") Long plateId){
        if (plateId == null){
            return JsonResult.failure("参数有误");
        }
        List<Context> Contexts =  ContextService.selectListByPlateId(plateId);
        if (CollectionUtils.isEmpty(Contexts)){
            return JsonResult.unFound("暂无语境");
        }
        return JsonResult.success(Contexts);
    }

    /**
     * 查询一级语境下的二级语境
     * @param contextId 板块
     */
    @ApiOperation(value="查询一级语境下的二级语境",notes="查询一级语境下的二级语境接口")
    @GetMapping(value="/plate/sonontextList/{contextId}")
    public JsonResult getSonContextList(@ApiParam(value = "语境Id",type = "Long",required = true)@PathVariable(value = "contextId") Long contextId){
        if (contextId == null){
            return JsonResult.failure("参数有误");
        }
        List<Context> Contexts =  ContextService.selectListByContextId(contextId);
        if (CollectionUtils.isEmpty(Contexts)){
            return JsonResult.unFound("暂无子语境");
        }
        return JsonResult.success(Contexts);
    }

    /**
     * 删除对话
     * @param dialogueId 对话
     */
    @ApiOperation(value="删除对话",notes="删除对话")
    @DeleteMapping(value="/plate/context/dialogue/{dialogueId}")
    public JsonResult delDialogue(@ApiParam(value = "对话Id",type = "Long",required = true)@PathVariable(value = "dialogueId") Long dialogueId){
        if (dialogueId == null){
            return JsonResult.failure("参数有误");
        }
        if (dialogueService.selectByPrimaryKey(dialogueId) == null){
            return JsonResult.failure("对话信息不存在");
        }
        int result = dialogueService.deleteByPrimaryKey(dialogueId);
        if (result > 0){
            return JsonResult.success("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

    /**
     * 查询模板下所有对话
     * @param templateId 模板Id
     * @param isSys 0系统模板1为用户模板
     */
    @ApiOperation(value="查询模板下所有对话",notes="查询模板下所有对话接口")
    @GetMapping(value="/plate/dialogueList/{templateId}/{isSys}")
    public JsonResult getDialogueList(
            @ApiParam(value = "模板Id",type = "Long",required = true)@PathVariable(value = "templateId") Long templateId,
            @ApiParam(value = "0系统板块1为用户模块",type = "Integer",required = true)@PathVariable(value = "isSys") Integer isSys){
        if (templateId == null || isSys == null){
            return JsonResult.failure("参数有误");
        }
        //若为系统模板
        if (isSys == 0){
            TemplateAll TemplateAll = TemplateAllService.selectByPrimaryKey(templateId);
            if (TemplateAll == null){
                return JsonResult.unFound("无此模板，或已被删除");
            }
            if (TemplateAll != null){
                List<Dialogue> Dialogues= dialogueService.selectDialogueListByTemplateId(templateId);
                    return JsonResult.success("查询成功",Dialogues);
            }
        }
        if (isSys == 1){
            //若为用户模板
            TemplateUser TemplateUser = templateUserService.selectByPrimaryKey(templateId);
            if (TemplateUser != null){
                List<Dialogue> dialogues= dialogueService.selectDialogueListByUserTemplateId(templateId);
                    return JsonResult.success("查询成功",dialogues);
                }
            return JsonResult.unFound("无此模板，或已被删除");
        }
        return JsonResult.error("type参数有误");
    }

    /**
     * 更新对话url
     * @param saveFiles 将文件路径和id保存到数据库中
     */
    @ApiOperation(value="更新对话url",notes="更新对话url接口")
    @PutMapping(value="/plate/dialogueList/saveUrls")
    public JsonResult updateDialogue(@RequestBody SaveFiles saveFiles){
        if (saveFiles == null ||CollectionUtils.isEmpty( saveFiles.getSaveFiles() )){
            return JsonResult.failure("参数有误");
        }
        List<String> ids = new ArrayList<String>();
        List<SaveFile> sfiles = saveFiles.getSaveFiles();
        sfiles.forEach((saveFile)->{
            Long id = saveFile.getId();
            if (id != null){
                String url =saveFile.getUrl();
                if (StringUtils.isEmpty(url)){
                    ids.add(id.toString());
                }
                Dialogue dialogue = dialogueService.selectByPrimaryKey(id);
                if (dialogue != null){
                    dialogue.setVideoUrl(url);
                    dialogueService.updateByPrimaryKey(dialogue);
                }
            }
        });
        if (ids.size() > 0){
            String s = "";
            for (int i = 0; i<ids.size() ;i++) {
                if (i == ids.size() -1 || i == 0){
                    s += ids.get(i);
                }else {
                    s += ids.get(i) + ",";
                }
            }
            return JsonResult.success("更新成功","id为" + s + "的url为空，请重试");
        }
        return JsonResult.success("更新成功");
    }
}