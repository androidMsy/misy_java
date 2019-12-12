package com.misy.mybatis.enums;

public enum  ResultCodeEnum {
    /**
     * 调用成功
     */
    SUCCESS(0, "成功"),

    /**
     * 调用失败
     */
    FAIL(-1, "失败");


    /**
     * 返回编码
     */
    private Integer status;

    /**
     * 编码对应的消息
     */
    private String msg;

    ResultCodeEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 获取枚举类型的编码值
     */
    public Integer status() {
        return this.status;
    }

    /**
     * 获取枚举类型的编码含义
     */
    public String msg() {
        return this.msg;
    }

    /**
     * 根据枚举名称--获取枚举编码
     */
    public static Integer getCode(String name) {
        for (ResultCodeEnum resultCode : ResultCodeEnum.values()) {
            if (resultCode.name().equals(name)) {
                return resultCode.status;
            }
        }
        return null;
    }
}
