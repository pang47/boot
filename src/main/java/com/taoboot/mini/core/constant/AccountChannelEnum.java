package com.taoboot.mini.core.constant;

/**
 *  渠道字典
 *
 * @author chentao
 * @create 2019/9/9
 * @since 1.0.0
 */
public enum AccountChannelEnum {

    ALICHANNEL("ALI", "支付宝"),
    HUABEICHANNEL("HUABEI", "花呗"),
    ZHAOHANGCHANNEL("CMB", "招商银行"),
    JIANHANGCHANNEL("CCB", "建设银行"),
    GONGHANGCHANNEL("ICBC" , "工商银行"),
    WECHATCHANNEL("WX", "微信"),
    DEBTCHANNEL("DEBT", "外债"),
    JINGDONGCHANNEL("JD", "京东"),
    ;

    private String code; //渠道ID
    private String value; //渠道名字

    AccountChannelEnum(String code, String value){
        this.code = code;
        this.value = value;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }}