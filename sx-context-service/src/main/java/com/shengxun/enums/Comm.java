package com.shengxun.enums;

import java.util.Arrays;
import java.util.List;

public class Comm {

    //==============全局语境常量=================//
    public static final Integer KE_YONG=1; //可用
    public static final Integer BU_KE_YONG=0; // 不可用；
    public static final Integer TEMP_LEVEL=1; //级别为1 为父节点；
    public static final String VOICE_URL_NULL=""; //没有语音内容；
    public static final Integer QJHY_LEVLE_PARENT = 1;
    public static final Integer WEI_WAN_JU_JUE=5; //委婉拒绝
    public static final Integer YAO_QIU_CHONG_FU=3; //要求重复

    public static void main(String[] args) {
        String[] str = {"1","2","3"};
        List<String> list = Arrays.asList(str);
        boolean b = list.contains("1");
        System.out.println(b);
        boolean remove = list.remove("1");
        for (int i=0;i<100;i++){
            System.out.println(list.get(i));
        }

    }

}
