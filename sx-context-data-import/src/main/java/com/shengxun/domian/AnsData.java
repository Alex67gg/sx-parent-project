package com.shengxun.domian;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ChenWei
 * on 2018/7/3 15:53.
 */
@Data
@AllArgsConstructor
public class AnsData {
    private String name;
    private String keyWord;
    private String word;
    private String nextName;
    private String record;
    private String skipCondition;
    private String skipTo;

}
