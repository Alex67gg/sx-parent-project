package com.shengxun.controller.industry;

import com.shengxun.controller.base.BaseController;
import com.shengxun.entity.IndustryAll;
import com.shengxun.entity.IndustryUser;
import com.shengxun.entity.TemplateAll;
import com.shengxun.entity.TemplateUser;
import com.shengxun.enums.Comm;
import com.shengxun.result.JsonResult;
import com.shengxun.result.vo.DataIndustryDelVo;
import com.shengxun.result.vo.DataInfo;
import com.shengxun.result.vo.DataVo;
import com.shengxun.result.vo.IndustryVo;
import com.shengxun.service.IndustryAllService;
import com.shengxun.service.IndustryUserService;
import com.shengxun.service.TemplateAllService;
import com.shengxun.service.TemplateUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by ldh on 2018/6/11.
 * 全局的行业树控制类；
 */
@Api(value = "全局行业和用户行业controller", tags = {"全局行业和用户行业接口"})
@RestController(value = "/industry")
public class IndustryController extends BaseController {

    @Autowired
    private IndustryAllService industryAllService;
    @Autowired
    private TemplateAllService templateAllService;

    @Autowired
    private IndustryUserService industryUserService;
    @Autowired
    private TemplateUserService templateUserService;

    private static final Logger logger = LoggerFactory.getLogger(IndustryController.class);
    private static final Long INDUSTRY_TYPE_USER = 1L; //用户
    private static final Long INDUSTRY_TYPE_ALL = 0L; //全局

    /**
     * @return
     */
    @ApiOperation(value = "行业的树形列表展示", notes = "调用时type=1为用户行业，type=0 为全局行业")
    @PostMapping(value = "/industryTreeList")
    public JsonResult getIndustryTree(@RequestBody DataInfo dataInfo) {

            if(null != dataInfo.getType() &&INDUSTRY_TYPE_USER==dataInfo.getType()){
                if(dataInfo.getUserId() != null) {
                    logger.info("查询用户行业树列表接口industryTreeList");
                    List<IndustryUser> list = industryUserService.getUserTreeListById(dataInfo.getUserId());
                    return JsonResult.success("查询成功", list);
                }
                return JsonResult.failure("查询成功");
            }else if(null != dataInfo.getType() &&INDUSTRY_TYPE_ALL==dataInfo.getType()){
                logger.info("查询全局行业树列表接口industryTreeList");
                try {
                    //查询全局行业树列表；
                    List<IndustryAll> qjhies = industryAllService.selectIndustryTreeList();
                    return JsonResult.success("查询成功", qjhies);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return JsonResult.failure("查询失败");
            }
            return JsonResult.failure("查询失败");
    }


    /**
     * 删除全局行业信息,根据父节点 删除行业信息，包括行业下面的模板；
     *
     * @param dataInfo 全局行业父节点
     * @return
     */
    @ApiOperation(value = "删除父节点", notes = "删除行业父节点接口")
    @RequestMapping(value = "/industry", method = {RequestMethod.DELETE})
    public JsonResult delete(@RequestBody DataIndustryDelVo dataInfo) {
        if(null != dataInfo.getType() && INDUSTRY_TYPE_ALL==dataInfo.getType() && null != dataInfo.getIndustryId()){
            logger.info("全局行业树的删除接口");
                List<TemplateAll> qjmbs = templateAllService.selectByIndustry_idAll(dataInfo.getIndustryId());
                if (qjmbs != null && qjmbs.size() > 0) {
                    //删除子节点
                    for (int i = 0; i < qjmbs.size(); i++) {
                        templateAllService.updateStatusById(qjmbs.get(i).getId(), Comm.BU_KE_YONG);
                    }
                    industryAllService.updateStatus(dataInfo.getIndustryId(), Comm.BU_KE_YONG);
                    return JsonResult.success("删除成功");
                } else {
                    //直接删除父节点
                    industryAllService.updateStatus(dataInfo.getIndustryId(), Comm.BU_KE_YONG);
                    return JsonResult.success("删除成功");
                }
        }else if(null != dataInfo.getType() && INDUSTRY_TYPE_USER==dataInfo.getType() && null != dataInfo.getIndustryId()){
            int result = industryUserService.deleteByPrimaryKey(dataInfo.getIndustryId());
            //根据用户的模板id查询用户模板；
            List<TemplateUser> list = templateUserService.selectByFoderId(dataInfo.getIndustryId());
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    templateUserService.deleteByPrimaryKey(list.get(i).getId());
                }
            }
            return JsonResult.success("删除成功");
        }
        logger.info("行业树的删除接口");
        return JsonResult.failure("删除失败");
    }


    @ApiOperation(value = "添加全局行业", notes = "根据name添加全局行业")
    @PostMapping(value = "/IndustryAdd")
    public JsonResult addNewIndustry(@RequestBody DataVo dataVo) {
            if(null != dataVo.getType() && INDUSTRY_TYPE_ALL==dataVo.getType()){
                   if( !StringUtils.isEmpty(dataVo.getName())){
                       IndustryAll qjhy = new IndustryAll();
                       qjhy.setCreateTime(new Date());
                       qjhy.setLevel(Comm.QJHY_LEVLE_PARENT);
                       qjhy.setStatus(Comm.KE_YONG);
                       qjhy.setIndustryName(dataVo.getName());
                       qjhy.setIndustrySort(Comm.TEMP_LEVEL);
                       industryAllService.insert(qjhy);
                       return JsonResult.success("添加成功");
                   }
                   return JsonResult.failure("添加失败");
            }else if(null != dataVo.getType() && INDUSTRY_TYPE_USER==dataVo.getType() && null != dataVo.getUserId() && !StringUtils.isEmpty(dataVo.getName())){
                    IndustryUser industryUser = new IndustryUser();
                    industryUser.setUserId(dataVo.getUserId());
                    industryUser.setFolderName(dataVo.getName());
                    industryUser.setCreateTime(new Date());
                    int result = industryUserService.insert(industryUser);
                    if (result < 1) {
                        return JsonResult.failure("保存失败");
                    }
                    return JsonResult.success("保存成功");
                }
            return JsonResult.failure("添加失败");
        }

    /**
     * 全局行业重命名
     * @param
     * @param dataVo 新名称；
     * @return
     */
    @ApiOperation(value = "全局行业重命名", notes = "根据id修改全局行业的名称")
    @PutMapping(value = "/IndustryReName")
    public JsonResult reName(@RequestBody IndustryVo dataVo) {
        if (null !=dataVo.getType() && INDUSTRY_TYPE_ALL == dataVo.getType() && !StringUtils.isEmpty(dataVo.getName()) && null != dataVo.getIndustryId()){
            logger.info("用全局行业树父节点重命名");
            industryAllService.updateReNameById(dataVo.getIndustryId(), dataVo.getName());
            return JsonResult.success("修改成功");
        }else if(null !=dataVo.getType() && INDUSTRY_TYPE_USER == dataVo.getType() && !StringUtils.isEmpty(dataVo.getName()) && null != dataVo.getIndustryId()){
            logger.info("用户行业树父节点重命名");
            IndustryUser ids = new IndustryUser();
            ids.setUpdateTime(new Date());
            ids.setFolderName(dataVo.getName());
            ids.setId(dataVo.getIndustryId());
            int result = industryUserService.update(ids);
            if (result < 1) {
                return JsonResult.failure("修改失败");
            }
            return JsonResult.success("修改成功");
        }
        return JsonResult.failure("修改失败");
    }


    //=============================用户行业==================================================

    /**
     * 新增用户行业父目录
     *
     * @param id
     * @param name
     * @return
     */
    /*@ApiOperation(value = "新增用户目录", notes = "新增用户行业树父节点接口，uid为用户id,name是添加的名称")
    @RequestMapping(value = "/IndustryAdd/{uid}/{name}", method = {RequestMethod.POST})
    public JsonResult saveUserIndustry(
            @ApiParam(value = "用户Id", type = "Long", required = true) @PathVariable(value = "uid") Long id,
            @ApiParam(value = "用户行业名称", type = "String", required = true) @PathVariable(value = "name") String name) {
        logger.info("添加用户行业接口");
        IndustryUser industryUser = new IndustryUser();
        industryUser.setUserId(id);
        industryUser.setFolderName(name);
        industryUser.setCreateTime(new Date());
        int result = industryUserService.insert(industryUser);
        if (result < 1) {
            return JsonResult.failure("保存失败");
        }
        return JsonResult.success("保存成功");
    }*/

    /**
     *保存folder信息
     * @param folder
     * @return
     */
   /* @ApiOperation(value="保存用户目录", notes="保存话术目录接口")
    @RequestMapping(value = "/folder", method = {RequestMethod.POST })
    public JsonResult save(@RequestBody IndustryUser folder){
        logger.info("[IndustryUserController][save][folder——>]" + folder.toString());
        int result = industryUserService.insert(folder);
        if (result < 1){
            return JsonResult.failure("保存失败");
        }
        return JsonResult.success("保存成功");
    }*/


    /**
     * 修改folder信息
     *
     * @param folder
     * @return
     */
  /*  @ApiOperation(value="修改用户目录", notes="修改话术目录接口")
    @RequestMapping(value = "/folder", method = {RequestMethod.PUT })
    public JsonResult update(@RequestBody IndustryUser folder){
        logger.info("[IndustryUserController][update][folder——>]" + folder.toString());
        int result = industryUserService.update(folder);
        if (result < 1){
            return JsonResult.failure("修改失败");
        }
        return JsonResult.success("修改成功");
    }
*/
   /* @ApiOperation(value = "修改用户行业父节点名称", notes = "修改用户行业父节点名称接口，uid是用户行业id，name是新的名称")
    @RequestMapping(value = "/IndustryReName/{uid}/{name}", method = {RequestMethod.PUT})
    public JsonResult update(@ApiParam(value = "用户行业父节点id", type = "Long", required = true) @PathVariable(value = "uid") Long id,
                             @ApiParam(value = "用户行业父节点新名称", type = "String", required = true) @PathVariable(value = "name") String name) {
        logger.info("用户行业树父节点重命名");
        IndustryUser ids = new IndustryUser();
        ids.setUpdateTime(new Date());
        ids.setFolderName(name);
        ids.setId(id);
        int result = industryUserService.update(ids);
        if (result < 1) {
            return JsonResult.failure("修改失败");
        }
        return JsonResult.success("修改成功");
    }*/


    /**
     * 删除用户行业父节点信息
     *
     * @param id
     * @return
     */
    /*@ApiOperation(value = "删除用户行业父节点", notes = "删除话术目录接口")
    @RequestMapping(value = "/industry/{uId}", method = {RequestMethod.DELETE})
    public JsonResult delete(@PathVariable(value = "uId") Long id) {
        logger.info("[IndustryUserController][delete][id——>]" + id.toString());
        int result = industryUserService.deleteByPrimaryKey(id);
        //根据用户的模板id查询用户模板；
        List<TemplateUser> list = templateUserService.selectByFoderId(id);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                templateUserService.deleteByPrimaryKey(list.get(i).getId());
            }
        }
        return JsonResult.success("删除成功");
    }*/

    /**
     *查询folder信息
     * @param userId
     * @return
     */
 /*   @ApiOperation(value="查询用户目录", notes="查询话术目录接口")
    @RequestMapping(value = "/folder/{userId}", method = {RequestMethod.GET })
    public JsonResult get(@PathVariable(value="userId")Long userId){
        logger.info("[IndustryUserController][get][userId——>]" + userId.toString());
        List<IndustryUser> sx_folder_users = industryUserService.selectListByUserId(userId);
        return JsonResult.success("查询成功",sx_folder_users);
    }*/




}