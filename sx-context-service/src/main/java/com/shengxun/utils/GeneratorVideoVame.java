package com.shengxun.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ldh on 2018/6/14.
 *
 */
@Component
public class GeneratorVideoVame {

    @Autowired
    private PinyinTool pinyinTool;

    /**
     * 提取汉语首字母组成大写字符串
     * @param projectName 项目名称
     * @return
     */
    public  String getVideoVame(String projectName)throws Exception{
        String videoVame = pinyinTool.getAlpha(projectName);
        return videoVame;
    }
}