package com.shengxun.result.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ldh on 2018/7/11.
 */
@ApiModel(value="",description="批量保存id和文件地址")
public class SaveFiles implements Serializable {
    private List<SaveFile> saveFiles;

    public List<SaveFile> getSaveFiles() {
        return saveFiles;
    }

    public void setSaveFiles(List<SaveFile> saveFiles) {
        this.saveFiles = saveFiles;
    }

    public SaveFiles(List<SaveFile> saveFiles) {
        this.saveFiles = saveFiles;
    }

    public SaveFiles() {
    }
}
