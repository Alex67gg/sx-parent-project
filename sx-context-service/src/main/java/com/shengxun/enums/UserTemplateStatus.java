package com.shengxun.enums;

/**
 * Created by ldh on 2018/6/13.
 * 用户模板状态(-1.删除1·待审核2·使用中3·停用4·无模板)
 */
public enum UserTemplateStatus {

    DEL("删除",-1),

    TOAUDIT("待审核",1),

    INUSE("使用中",2),

    DISABLE("停用",3),

    NOTEMPLATE ("无模板",4);

    private String name;
    private int value;

    // 构造方法
    private UserTemplateStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static String getName(int value) {
        for (UserTemplateStatus c : UserTemplateStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }

    public static int getValue(UserTemplateStatus templateStatus) {
        for (UserTemplateStatus c : UserTemplateStatus.values()) {
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
