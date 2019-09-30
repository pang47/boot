package com.taoboot.mini.core.common;


/**
 *  TAO自定义异常信息
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public class TaoMiniException extends Exception {

    private String errorCode;

    private String errorMsg;

    public TaoMiniException(){
        this.errorCode = "-1";
        this.errorCode = "未知错误";
    }

    public TaoMiniException(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public TaoMiniException(String errorMsg){
        this("-1", errorMsg);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}