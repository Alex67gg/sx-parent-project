package com.shengxun.result.vo;

import com.shengxun.entity.ContextAll;

import java.util.List;

/**
 * 接收参数时全局语境的vo;
 */
public class ContextAllVo {
    //问题未识别；
    private ContextAll problemNotidentified;

    //一般语句未识别
    private ContextAll commonly;

    //超时

    private ContextAll timeOut;


    //要求重复

    private ContextAll repeat;


    //委婉拒绝；

    private ContextAll refuse;

    public ContextAll getProblemNotidentified() {
        return problemNotidentified;
    }

    public void setProblemNotidentified(ContextAll problemNotidentified) {
        this.problemNotidentified = problemNotidentified;
    }

    public ContextAll getCommonly() {
        return commonly;
    }

    public void setCommonly(ContextAll commonly) {
        this.commonly = commonly;
    }

    public ContextAll getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(ContextAll timeOut) {
        this.timeOut = timeOut;
    }

    public ContextAll getRepeat() {
        return repeat;
    }

    public void setRepeat(ContextAll repeat) {
        this.repeat = repeat;
    }

    public ContextAll getRefuse() {
        return refuse;
    }

    public void setRefuse(ContextAll refuse) {
        this.refuse = refuse;
    }

    public void ContextAll(){}
}
