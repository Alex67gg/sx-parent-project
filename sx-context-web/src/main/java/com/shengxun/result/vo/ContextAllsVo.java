package com.shengxun.result.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shengxun.entity.ContextAll;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContextAllsVo {

    private List<List<ContextAll>> layer;

    public List<List<ContextAll>> getLayer() {
        return layer;
    }

    public void setLayer(List<List<ContextAll>> layer) {
        this.layer = layer;
    }

    public ContextAllsVo(List<List<ContextAll>> layer) {
        this.layer = layer;
    }
    public ContextAllsVo(){}
}
