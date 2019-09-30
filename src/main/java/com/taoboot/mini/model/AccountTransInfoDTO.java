package com.taoboot.mini.model;

import java.io.Serializable;

/**
 *
 * @author chentao
 * @create 2019/9/9
 * @since 1.0.0
 */
public class AccountTransInfoDTO implements Serializable{
    private String accountTransId;
    private String money;   //本次上传金额
    private String crtDate;
    private String crtTime;
    private String user;    //所属人
    private String uploadUser; //上传人
    private String channel;
    private String channelName;
    private String beforeBalance; //修改前金额

    public String getAccountTransId() {
        return accountTransId;
    }

    public void setAccountTransId(String accountTransId) {
        this.accountTransId = accountTransId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(String crtDate) {
        this.crtDate = crtDate;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(String beforeBalance) {
        this.beforeBalance = beforeBalance;
    }
}