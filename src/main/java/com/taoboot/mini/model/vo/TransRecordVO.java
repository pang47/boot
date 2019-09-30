package com.taoboot.mini.model.vo;



import com.taoboot.mini.model.TransRecordDTO;

import java.util.List;

/**
 * 界面VO
 *
 * @author chentao
 * @create 2019/9/7
 * @since 1.0.0
 */
public class TransRecordVO {

    private String transDate;
    private String total;
    private List<TransRecordDTO> data;

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<TransRecordDTO> getData() {
        return data;
    }

    public void setData(List<TransRecordDTO> data) {
        this.data = data;
    }
}