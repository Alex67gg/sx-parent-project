package com.shengxun.enums;

/**
 * Created by ldh on 2018/6/12.
 * 模板状态
 */
public enum TemplateStatus {

    del("删除状态",-1),

    disabled("不可用",0),

    usable("可用",1);



    private String name;
    private int value;

    // 构造方法
    private TemplateStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static String getName(int value) {
        for (TemplateStatus c : TemplateStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }

    public static int getValue(TemplateStatus templateStatus) {
        for (TemplateStatus c : TemplateStatus.values()) {
            if (c.getValue() == templateStatus.getValue()) {
                return c.value;
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}