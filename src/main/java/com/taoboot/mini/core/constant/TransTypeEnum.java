package com.taoboot.mini.core.constant;

public enum TransTypeEnum {
    BREAKFASTTRANS("1", "早餐"),
    LUNCHTRANS("2", "午餐"),
    DINNERTRANS("3", "晚餐"),
    HAPPYTRANS("4", "娱乐"),
    FRIENDSTRANS("5", "交友"),
    CLOTHERTRANS("6", "服饰"),
    BUSTRANS("7", "交通"),
    XIGUATRANS("8", "西瓜"),
    HOUSETRANS("9", "住房"),
    QINQITRANS("10", "亲戚"),
    SHUMATRANS("11", "数码"),
    MEIRONGTRANS("12", "美容"),
    LINGSHITRANS("13", "零食"),
    LVXINGTRANS("14", "旅行"),
    JUJIATRANS("15", "居家"),
    OHTERTRANS("16", "额外"),
    SUPERMARKETTRANS("17", "超市"),
    TAOBAOTRANS("18", "淘宝"),
    ;

    private String transType;
    private String transTypeName;

    TransTypeEnum(String transType, String transTypeName){
        this.transType = transType;
        this.transTypeName = transTypeName;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }
}
