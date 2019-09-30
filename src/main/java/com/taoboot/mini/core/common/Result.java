package com.taoboot.mini.core.common;

import java.io.Serializable;

/**
 * 〈统一返回信息〉
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public class Result<T> implements Serializable {
    private boolean succ;   //是否成功标识
    private T Data;         //返回信息
    private String retMsg;  //返回信息

    public Result(){
        this.retMsg = "交易成功";
        this.succ = true;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}