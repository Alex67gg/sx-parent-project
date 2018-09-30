package com.shengxun.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ChenWei
 * on 2018/7/5 16:34.
 */
public class AtomicIntegerUtils {
    // 将线程和atomicNum绑定，保证事务能统一执行
    private static ThreadLocal<AtomicInteger> atomicIntegerThreadLocal = new ThreadLocal<AtomicInteger>();

    // 获得当前atomicNum
    public static AtomicInteger getCurrentAtomicInteger() {
        // 默认线程里面取
        AtomicInteger atomicInteger = atomicIntegerThreadLocal.get();
        if (atomicInteger == null) {
            atomicInteger = getAtomicInteger();
        }
        return atomicInteger;
    }

    // 获得atomicNum
    private static AtomicInteger getAtomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicIntegerThreadLocal.set(atomicInteger);
        return atomicInteger;
    }

    //移除atomicNum
    public static void removeAtomicNum() {
        atomicIntegerThreadLocal.remove();
    }
}
