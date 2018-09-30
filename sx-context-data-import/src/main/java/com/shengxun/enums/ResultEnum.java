package com.shengxun.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by chenwei
 * on 2018/1/10 下午4:06.
 */
@AllArgsConstructor
public enum ResultEnum {
    INSERT_REQ_ANS_DATA_WRONG(600, "插入主表报错"),
    GET_NEXT_ID_WRONG(601, "没有下个板块或者模版的id"),
    CONN_CLOSE_WRONG(602, "关闭数据库连接报错"),
    WRITE_IS_WRONG(603, "写入文本出错"),
    INSERT_CMP_NO_WRONG(604, "插入词性表出错"),
    INSERT_PUBLIC_DATA_WRONG(605, "插入全局语境表出错"),
    INSERT_FLOW_WRONG(606, "插入流程表出错"),
    INSERT_INFO_WRONG(607, "插入版本信息表出错"),
    PACK_WRONG(608, "将用户的源文件打包出错"),
    COMPRESS_WRONG(609, "将用户的源文件压缩出错"),
    CREATE_DATA_WRONG(610, "生成数据出错"),
    PLATES_IS_NULL(611, "该模版下没有板块"),
    PACK_COMPRESS_WRONG(612, "打包压缩时报错"),
    INDUSTRY_ID_IS_NULL(613,"行业类型查询是空");

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String msg;
}
