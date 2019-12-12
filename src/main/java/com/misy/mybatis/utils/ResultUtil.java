package com.misy.mybatis.utils;

import com.misy.mybatis.enums.ResultCodeEnum;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResultUtil<T> implements Serializable {
    private static final long serialVersionUID = 3436477890959388499L;

    /**
     * 返回操作码（默认为正常）
     */
    private Integer status = ResultCodeEnum.SUCCESS.status();

    /**
     * 返回数据信息
     */
    private T data;

    /**
     * 返回正确消息信息
     */
    private String msg = "";

    public static ResultUtil success = new ResultUtil();
    public static ResultUtil fail = new ResultUtil().status(ResultCodeEnum.FAIL.status());

    // 构造方法
    public static ResultUtil build() {
        return new ResultUtil();
    }

    public static <T> ResultUtil<T> build(T data) {
        return new ResultUtil().data(data);
    }

    // 赋值方法
    public ResultUtil data(T data) {
        this.data = data;
        return this;
    }

    public ResultUtil status(Integer status) {
        this.status = status;
        return this;
    }

    public ResultUtil msg(String msg) {
        this.msg = msg;
        return this;
    }
}
