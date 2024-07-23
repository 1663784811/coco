package com.cyyaw.cui.enums;

/**
 * 状态
 */
public enum CuiStatusEnum {

    NORMALS("正常"),


    LOADING("加载"),

    DISABLED("禁用");

    private String note;

    CuiStatusEnum(String note) {
        this.note = note;
    }
}
