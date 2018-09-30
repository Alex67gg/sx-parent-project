package com.shengxun.controller.plate;

import com.shengxun.entity.ContextAll;
import com.shengxun.enums.Comm;
import com.shengxun.fastdfs.FastDFSClientWrapper;
import com.shengxun.result.JsonResult;
import com.shengxun.result.vo.ContextAllVo;
import com.shengxun.result.vo.ContextAllsVo;
import com.shengxun.service.ContextAllService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
import java.util.logging.Logger;

@RestController
/**
 * 全局语境操作的controller
 */
@Api(value = "全局语境controller", tags = {"全局语境接口"})
public class ContextAllController {
    private static Logger log = Logger.getLogger("ContextLog");

    @Autowired
    private ContextAllService contextAllService;
    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    /**
     * 根据类型和模板id查询全局语境；
     *
     * @return
     */
    @ApiOperation(value = "根据类型和模板id查询全局语境", notes = "根据类型和模板id查询全局语境回显接口")
    @GetMapping(value = "/selectContextAll/{type}/{templateId}")
    public JsonResult SelectContextAll(@ApiParam(value = "所属模板类型：0是全局模板 1为用户模板", type = "Integer") @PathVariable(value = "type") Integer type,
                                       @ApiParam(value = "所属模板的模板id", type = "Long") @PathVariable(value = "templateId") Long tempId) {

        log.info("进入到全局语境查询接口 type:" + type + "-templateId:" + tempId);
        HashMap<Integer, Object> map = contextAllService.selectContextAllByAll(tempId, type, null);
        return JsonResult.success("查询成功", map);
    }

    /**
     * 添加全局语境
     *
     * @param data
     * @return
     */
//    @ApiOperation(value = "添加全局语境对话", notes = "添加全局语境对话的接口")
//    @PostMapping(value = "/addContextAll")
//    public JsonResult addContext(@ApiParam(value = "全局语境内容", type = "object") @RequestBody HashMap<Integer, ContextAllsVo> data) throws Exception {
//        log.info("添加全局语境 ");
//        if (data != null) {
//            contextAllService.addContextAll(data);
//            return JsonResult.success("添加成功");
//        }
//        return JsonResult.success("添加失败");
//    }


    /**
     * 修改全局语境
     *
     * @param data 全局语境内容；
     * @return
     */

    @ApiOperation(value = "修改或者添加全局语境内容", notes = "修改或者添加全局语境内容的接口")
    @PutMapping(value = "/updateOrAddContextAll")
    public JsonResult updateContextAll(@ApiParam(value = "全局语境内容", type = "Object", required = true) @RequestBody HashMap<Integer, ContextAllsVo> data) throws Exception {
        log.info("修改全局语境内容 updateContextAll");
        if (data != null) {
            contextAllService.updateContextAlls(data);
            return JsonResult.success("修改成功");
        }
        return JsonResult.failure("修改失败");
    }

    /**
     * 添加关键字
     * @param id  全局语境id
     * @param keyWord 关键字
     * @return
     */
  /*  @ApiOperation(value = "全局语境添加关键字",notes = "全局语境添加关键字的接口")
    @PostMapping(value = "/addContextKeyWord/{id}/{key}")
    public JsonResult addContextKeyWord(Long id,String keyWord) throws Exception{
        ContextAll qjyj = null;
        if(id!=null && ! StringUtils.isEmpty(keyWord)){
            qjyj = contextAllService.selectContextById(id);
            String word = qjyj.getKey_word();
            String words = word + "," + keyWord;
            qjyj.setKey_word(words);
            contextAllService.updateContextAll(qjyj);
            return JsonResult.success("添加成功");
        }
        return JsonResult.success("添加失败");
    }*/


    /**
     * 删除全局语境关键字的接口；
     *
     * @param id      全局语境id；
     * @param  ；
     * @return
     *//*
    @ApiOperation(value = "删除全局语境中的关键字", notes = "删除全局语境中的关键字接口")
    @DeleteMapping(value = "/delConKeyWord/{id}/{keyWord}")
    public JsonResult delContextKeyWord(@ApiParam(value = "全局语境id", type = "Long") @PathVariable(value = "id") Long id,
                                           @ApiParam(value = "全局语境的关键字", type = "String") @PathVariable(value = "keyWord") String keyWord) {
        ContextAll qjyj = null;
        log.info("进入到删除全局语境的关键字");
        try {
            if (id != null) {
                qjyj = contextAllService.selectContextById(id);
                String word = qjyj.getKey_word();
                String[] split = null;
                List<String> listKeyWord = null;
                if(!StringUtils.isEmpty(word)){
                    split = word.split(",");
                    listKeyWord = Arrays.asList(split);
                    log.info("con:"+listKeyWord.contains(keyWord));
                    for (int i =0;i<10;i++){
                        log.info("a："+listKeyWord.get(i));
                    }
                    if (listKeyWord.contains(keyWord)) {
                        for(int i=0;i<listKeyWord.size();i++){
                            SystemUtil.out.println("s"+i+":"+listKeyWord.get(i));
                    }
                        //删除关键字；
                        listKeyWord.remove(keyWord);
                        if(listKeyWord.size()<=0){
                            qjyj.setKey_word(null);
                        }else{
                            StringBuilder builder = new StringBuilder();
                            for (int i=0;i<listKeyWord.size();i++){
                                builder.append(listKeyWord.get(i)).append(",");
                            }
                            String str = builder.toString().substring(0, builder.toString().length() - 1);
                            SystemUtil.out.println("key-word："+str);
                            qjyj.setKey_word(str);
                        }
                        contextAllService.updateContextAll(qjyj);
                        return JsonResult.success("修改成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failure("修改失败");
    }*/

   /* @ApiOperation(value = "修改全局语境中循环语境的循环次数", notes = "修改全局语境的中循环语境的次数接口")
    @PutMapping(value = "/updateForCount/{id}/{count}")
    public JsonResult updateforCount(@ApiParam(value = "全局语境id", type = "Long") @PathVariable(value = "id") Long id,
                                     @ApiParam(value = "循环语境的循环次数", type = "Integer") @PathVariable(value = "count") Integer count) {
        ContextAll qjyj = null;
        try {
            if (id != null) {
                qjyj = contextAllService.selectContextById(id);
                if (count != null && count == qjyj.getFor_count()) {
                    return JsonResult.success("修改成功");
                }
                qjyj.setUpdate_time(new Date());
                qjyj.setFor_count(count);
                contextAllService.updateContextAll(qjyj);
                return JsonResult.success("修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failure("修改失败");
    }*/
    @ApiOperation(value = "删除全局语", notes = "根据id删除全局语境")
    @DeleteMapping(value = "/delContext/{id}")
    public JsonResult delContextById(@ApiParam(value = "语境id", type = "Long") @PathVariable(value = "id") Long id) {
        ContextAll qjyj = null;
        try {
            if (id != null) {
                qjyj = contextAllService.selectContextById(id);
                if (qjyj != null) {
                    contextAllService.delContextAll(qjyj);
                    return JsonResult.success("删除成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failure("删除失败");
    }


    @ApiOperation(value = "全局语境中音频文件的单个上传", notes = "上传全局语境中音频文件接口")
    @PostMapping(value = "/fileUpload/{id}")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file, @ApiParam(value = "全局语境id", type = "Long") @PathVariable(value = "id") Long id) {
        try {
            if (id != null) {
                String path = fastDFSClientWrapper.uploadFiles(file);
               //String filename = file.getOriginalFilename();
               // contextAllService.updateFileName(id, path, filename);
                return JsonResult.success("上传成功", path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.success("上传失败");
    }

   /* @ApiOperation(value = "测试HttpCode",notes = "测试HttpCode的接口")
    @PostMapping(value = "/httpCode/{id}/{name}")
    public  ResponseEntity<Map<Object, Object>> getHttp(@ApiParam(value = "id",type = "Long") @PathVariable(value = "id") Long id,
                                       @ApiParam(value = "name",type = "String")@PathVariable(value = "name") String name){
            log.info("参数为：id:"+id+",name:"+name);
        HashMap<Object, Object> map = new  HashMap<Object, Object>(0);
        return new ResponseEntity<Map<Object, Object>>(map,HttpStatus.OK);
    }*/


}
