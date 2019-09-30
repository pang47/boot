package com.taoboot.mini.core.dao;


import com.taoboot.mini.model.AccountTransInfoDTO;

import java.util.List;

public interface IAccountTransInfoMapper {

    void save(AccountTransInfoDTO accountTransInfoDTO);

    List<AccountTransInfoDTO> queryLimit(Integer length);

    List<AccountTransInfoDTO> queryByChannel(String channel);
}
