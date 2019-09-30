package com.taoboot.mini.core.dao;


import com.taoboot.mini.model.AccountInfoDTO;
import com.taoboot.mini.model.vo.AccountInfoVo;

import java.util.List;

/**
 *
 *
 * @author chentao
 * @create 2019/9/9
 * @since 1.0.0
 */
public interface IAccountInfoMapper {

    AccountInfoDTO getAccountInfoByUserIdAndChannel(AccountInfoDTO accountInfoDTO);

    void saveAccount(AccountInfoDTO accountInfoDTO);

    int updateAccount(AccountInfoDTO accountInfoDTO);

    List<AccountInfoVo> getAccountInfo();
}