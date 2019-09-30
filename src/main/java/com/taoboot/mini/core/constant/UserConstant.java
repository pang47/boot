package com.taoboot.mini.core.constant;

/**
 * 用户信息记录
 *
 * @author chentao
 * @create 2019/9/2
 * @since 1.0.0
 */
public enum UserConstant {

    TAOUSER(UserConstant.TAO, "桃"),
    SIQIUSER(UserConstant.SIQI, "胖");

    public static final String SIQI = "os6pc5afR1DsPCWbbzlFGNdPdND4";
    public static final String TAO = "os6pc5WIAMp_RAJitvZdGmlzMO6E";

    private String openId;
    private String userName;

    UserConstant(String openId, String userName){
        this.openId = openId;
        this.userName = userName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }}