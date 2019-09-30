package com.taoboot.mini.service;


import com.taoboot.mini.model.TransRecordDTO;
import com.taoboot.mini.model.vo.TransRecordVO;

import java.util.List;

public interface ITransRecordService {
    void saveRecord(TransRecordDTO dto);

    List<TransRecordDTO> getRecordByUserAndDate(String openId, String currDate);

    List<TransRecordVO> getRecordByUser(String openId);

    List<TransRecordVO> getRecordByUserAndDatePage(String openId, String transDate);

    String getPayByMonth(String month);

    TransRecordDTO getTransRecord(String transId);

    void deleteTransRecord(String transId);

    void updateTransRecord(TransRecordDTO dto);

    String[] getTransReportWeek();

    String[] getTransReportMonth();
}
