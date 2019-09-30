package com.taoboot.mini.core.dao;



import com.taoboot.mini.model.TransRecordDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public interface ITransRecordMapper {
    void saveRecord(TransRecordDTO dto);

    List<TransRecordDTO> getRecordByUserAndDate(TransRecordDTO dto);

    List<TransRecordDTO> getRecordByUser(TransRecordDTO dto);

    TransRecordDTO getTransRecord(String transId);

    int updateTransRecordStatus(TransRecordDTO dto);

    int updateTransRecord(TransRecordDTO dto);
}