package com.taoboot.mini.core.constant;


/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author chentao
 * @create 2019/6/3
 * @since 1.0.0
 */
public enum CacheConstant {

    WXOPENID("wxOpenId", "wxOpenId");

    private String code;
    private String label;

    CacheConstant(String code, String label){
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static CacheConstant getByCode(String code){
        for(CacheConstant cacheConstant : values()){
            if(cacheConstant.getCode().equals(code))
                return cacheConstant;
        }
        return null;
    }

}