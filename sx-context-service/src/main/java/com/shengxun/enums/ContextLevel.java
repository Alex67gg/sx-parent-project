package com.shengxun.enums;

/**
 * Created by ldh on 2018/6/14.
 * 语境层级枚举（1为一级语境，2为二级语境）
 */
public enum ContextLevel {
    /**
     * 1为一级语境
     */
    STAIR(1),
    /**
     * 2为二级语境
     */
    ACCESS(2);

    private int value;

    private ContextLevel(int value){
        this.value = value;
    }

    public static int getValue(ContextLevel contextLevel) {
        switch (contextLevel){
            case STAIR:
                return 1;
            case ACCESS:
                return 2;
             default:
                break;
        }
        return 0;
    }
}