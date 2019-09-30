package com.taoboot.mini.core.dao;


import com.taoboot.mini.model.FormInfoDTO;

/**
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public interface IFormInfoMapper {
    void saveFormInfo(FormInfoDTO dto);

    FormInfoDTO getFormInfoByOpenId(String openId);

    int updateFormInfo(FormInfoDTO dto);
}