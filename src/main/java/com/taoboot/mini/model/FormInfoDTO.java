package com.taoboot.mini.model;


import java.io.Serializable;

/**
 * 消息推送DTO
 *
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public class FormInfoDTO implements Serializable {

    private String formId;  //微信推送FORMID
    private String status;  //状态 0,未发送;1,发送成功;2,发送失败
    private String pushMsg; //推送消息
    private String openId;  //OPENID
    private String crtDate; //上传时间
    private String pushDate;    //推送日期
    private String pushTime;    //推送时间
    private String templete;    //推送模板
    private String errMsg; //错误信息

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(String crtDate) {
        this.crtDate = crtDate;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getTemplete() {
        return templete;
    }

    public void setTemplete(String templete) {
        this.templete = templete;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}