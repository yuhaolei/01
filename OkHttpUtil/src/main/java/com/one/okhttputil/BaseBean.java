package com.one.okhttputil;

public class BaseBean<T> {

    private String message;
    private int code;
    private String message_cn;
    private T data;

    public BaseBean() {
    }

    public BaseBean(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
