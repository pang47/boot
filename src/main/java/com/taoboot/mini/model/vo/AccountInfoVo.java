package com.taoboot.mini.model.vo;

import java.io.Serializable;

/**
 *
 * @author chentao
 * @create 2019/9/10
 * @since 1.0.0
 */
public class AccountInfoVo implements Serializable {

    private String channel;
    private String channelName;
    private String sumBalance;
    private String exp;

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

    public String getSumBalance() {
        return sumBalance;
    }

    public void setSumBalance(String sumBalance) {
        this.sumBalance = sumBalance;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}