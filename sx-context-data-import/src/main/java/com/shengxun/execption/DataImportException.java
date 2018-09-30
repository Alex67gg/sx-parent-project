package com.shengxun.execption;

import com.shengxun.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by chenwei
 * on 2018/1/10 下午4:13.
 */
public class DataImportException extends RuntimeException {

    @Setter
    @Getter
    private Integer code;

    public DataImportException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public DataImportException(ResultEnum resultEnum,Throwable throwable){
        super(resultEnum.getMsg(),throwable);
        this.code = resultEnum.getCode();
    }
}
