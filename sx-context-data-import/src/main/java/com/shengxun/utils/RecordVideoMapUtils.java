package com.shengxun.utils;

import java.util.HashMap;

/**
 * Created by ChenWei
 * on 2018/7/5 16:19.
 */
public class RecordVideoMapUtils {
    // 将线程和map绑定，保证事务能统一执行
    private static ThreadLocal<HashMap<String, String>> hashMapThreadLocal = new ThreadLocal<HashMap<String, String>>();

    // 获得当前map
    public static HashMap<String, String> getCurrentHashMap() {
        // 默认线程里面取
        HashMap<String, String> hashMap = hashMapThreadLocal.get();
        if (hashMap == null) {
            hashMap = getHashMap();
        }
        return hashMap;
    }

    // 获得map
    private static HashMap<String, String> getHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMapThreadLocal.set(hashMap);
        return hashMap;
    }

    //移除map
    public static void removeHashMap() {
        hashMapThreadLocal.remove();
    }
}
