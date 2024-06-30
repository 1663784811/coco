package com.cyyaw.coco.common.network;


import java.io.Serializable;

public class BaseResult<T> implements Serializable {

    /**
     * 分页信息
     */
    private Result result;
    /**
     * 主要数据 可能是Object 或  Arr
     */
    private T data;
    /**
     * 提示信息
     */
    private String msg;
    /**0.
     * 状态码
     */
    private Integer code;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static class Result {
        private Long total;
        private Integer page;
        private Integer size;

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }
    }





}