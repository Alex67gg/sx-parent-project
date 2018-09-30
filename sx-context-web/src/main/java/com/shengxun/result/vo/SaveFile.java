package com.shengxun.result.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by ldh on 2018/7/11.
 */
@ApiModel(value="",description="保存id和文件地址")
public class SaveFile implements Serializable {

    private Long id;

    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SaveFile(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public SaveFile() {
    }
}
