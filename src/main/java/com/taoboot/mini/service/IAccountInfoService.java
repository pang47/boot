package com.taoboot.mini.service;



import com.taoboot.mini.core.common.TaoMiniException;
import com.taoboot.mini.model.AccountInfoDTO;
import com.taoboot.mini.model.AccountTransInfoDTO;
import com.taoboot.mini.model.vo.AccountInfoVo;

import java.util.List;

public interface IAccountInfoService {
    List<AccountInfoVo> getAccountInfo();

    void saveOrUpdateAccountInfo(AccountInfoDTO accountInfoDTO);

    int updateAccountInfo();

    List<AccountTransInfoDTO> getAccountTransInfo(String length);

    String[] getAccountInfoByChannel(String channel) throws TaoMiniException;
}
