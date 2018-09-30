package com.shengxun.controller.upload;

import com.shengxun.fastdfs.FastDFSClientWrapper;
import com.shengxun.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ldh on 2018/6/20.
 * 上传文件Controller
 */
@RestController
@Api(value = "上传文件controller", tags = {"上传文件接口"})
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    /**
     * 上传单个文件方法
     *
     * @param file
     * @throws Exception
     */
    @ApiOperation(value = "上传单个文件", notes = "上传单个文件接口")
    @PostMapping(value = "/upload")
    public JsonResult upload(@RequestParam("file") MultipartFile file) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (file.isEmpty()) {
                return JsonResult.failure("Please select a file to upload");
            }
            if (file.getSize() >= 100*1024*1024){
                return JsonResult.failure("上传文件大小超过限制");
            }
            String fileUrl = fastDFSClientWrapper.uploadFile(file);
            map.put("fileUrl", fileUrl);
            logger.info("[UploadController][upload][fileUrl——>]" + fileUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure("上传失败");
        }
        return JsonResult.success(map);
    }

    /**
     * 上传多个文件方法
     *
     * @param files
     * @throws Exception
     */
    @ApiOperation(value = "上传多个", notes = "上传多个文件接口")
    @PostMapping(value = "/uploadFiles")
    public JsonResult uploadFiles(@RequestParam("files")MultipartFile[]  files) throws Exception {
        List<UploadStatus> list = new ArrayList<UploadStatus>();
        if (files.length<1) {
            return JsonResult.failure("Please select a file to upload");
        }
        try {
            for (MultipartFile multipartFile:files) {
                UploadStatus uploadStatus = new UploadStatus();
                String fileName ="";
                if (!multipartFile.isEmpty()){
                     fileName = multipartFile.getOriginalFilename();
                    String fileUrl = fastDFSClientWrapper.uploadFile(multipartFile);
                    uploadStatus.setStatus(1);
                    uploadStatus.setUrl(fileUrl);
                }else {
                    uploadStatus.setStatus(0);
                }
                uploadStatus.setFileName(fileName);
                list.add(uploadStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure("上传失败");
        }
        return JsonResult.success(list);
    }

    //上传返回实体
    @ApiModel(value="上传文件状态",description="UploadStatus")
    class UploadStatus{
        @ApiModelProperty(value="文件名称",name="fileName")
        private  String fileName;
        @ApiModelProperty(value="上传状态（0为未成功，1为成功）",name="status")
        private  Integer status;
        @ApiModelProperty(value="返回地址",name="url")
        private String url;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public UploadStatus(String fileName, Integer status, String url) {
            this.fileName = fileName;
            this.status = status;
            this.url = url;
        }

        public UploadStatus() {
        }

        @Override
        public String toString() {
            return "UploadStatus{" +
                    "fileName='" + fileName + '\'' +
                    ", status=" + status +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}