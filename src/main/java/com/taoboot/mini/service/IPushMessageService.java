package com.taoboot.mini.service;


import com.taoboot.mini.model.FormInfoDTO;

/**
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public interface IPushMessageService {
    void saveFormInfo(FormInfoDTO dto);

    void pushMessage(String templeteId);

    void pushMessage(String[] data, String templeteId);
}