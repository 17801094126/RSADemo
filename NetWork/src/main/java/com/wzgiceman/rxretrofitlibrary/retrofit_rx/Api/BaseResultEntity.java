package com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api;

/**
 * 回调信息统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity<T> {
    private boolean success;
    private String ret;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
