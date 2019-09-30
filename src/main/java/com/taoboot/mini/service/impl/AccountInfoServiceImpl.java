package com.taoboot.mini.service.impl;


import com.taoboot.mini.core.common.TaoMiniException;
import com.taoboot.mini.core.constant.UserConstant;
import com.taoboot.mini.core.dao.IAccountInfoMapper;
import com.taoboot.mini.core.dao.IAccountTransInfoMapper;
import com.taoboot.mini.model.AccountInfoDTO;
import com.taoboot.mini.model.AccountTransInfoDTO;
import com.taoboot.mini.model.vo.AccountInfoVo;
import com.taoboot.mini.service.IAccountInfoService;
import com.taoboot.mini.util.DateUtil;
import com.taoboot.mini.util.TaoMiniUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author chentao
 * @create 2019/9/9
 * @since 1.0.0
 */
@Service
@Transactional
public class AccountInfoServiceImpl implements IAccountInfoService {

    @Autowired
    IAccountInfoMapper accountInfoMapper;

    @Autowired
    IAccountTransInfoMapper accountTransInfoMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

    @Override
    public List<AccountInfoVo> getAccountInfo() {
        List<AccountInfoVo> retList = accountInfoMapper.getAccountInfo();
        for(AccountInfoVo vo : retList){
            String[] exps = vo.getExp().split(",");
            if(exps.length == 2){
                vo.setExp(exps[0] + "+" + exps[1]);
            }
            vo.setExp(vo.getExp() + "=" + vo.getSumBalance() + "元");
            vo.setChannelName(TaoMiniUtils.getChannelName(vo.getChannel()));
        }
        return retList;
    }

    @Override
    public void saveOrUpdateAccountInfo(AccountInfoDTO accountInfoDTO) {

        String channel = TaoMiniUtils.getChannel(accountInfoDTO.getChannelName());
        String user = UserConstant.TAOUSER.getUserName().equals(accountInfoDTO.getUserName())?UserConstant.TAOUSER.getOpenId():UserConstant.SIQIUSER.getOpenId();
        String money = "0";
        AccountInfoDTO queryAccount = getAccountInfo(user, channel);
        LOGGER.info("是否存在账户信息:{}", queryAccount != null);
        if(queryAccount == null){
            //插入
            accountInfoDTO.setChannel(channel);
            accountInfoDTO.setUser(user);
            accountInfoDTO.setCrtDate(DateUtil.getCurrDate());
            accountInfoDTO.setCrtTime(DateUtil.getCurrTime());
            accountInfoDTO.setUpdateDate(DateUtil.getCurrDate());
            accountInfoDTO.setUpdateTime(DateUtil.getCurrTime());
            accountInfoDTO.setAccountId(UUID.randomUUID().toString());
            accountInfoMapper.saveAccount(accountInfoDTO);
        }else{
            //更新
            money = queryAccount.getBalance();
            queryAccount.setUpdateDate(DateUtil.getCurrDate());
            queryAccount.setUpdateTime(DateUtil.getCurrTime());
            queryAccount.setBalance(accountInfoDTO.getBalance());
            accountInfoMapper.updateAccount(queryAccount);

            accountInfoDTO = queryAccount;
        }

        addAccountTransInfo(accountInfoDTO, money);

    }

    @Override
    public int updateAccountInfo() {
        return 0;
    }

    @Override
    public List<AccountTransInfoDTO> getAccountTransInfo(String length) {
        //默认5条
        if(StringUtils.isEmpty(length))
            length = "5";
        List<AccountTransInfoDTO> list = accountTransInfoMapper.queryLimit(Integer.parseInt(length));
        for(AccountTransInfoDTO dto : list)
            dto.setUser(TaoMiniUtils.getUserName(dto.getUser()));
        return list;
    }

    @Override
    public String[] getAccountInfoByChannel(String channel) throws TaoMiniException {
        List<AccountTransInfoDTO> list = accountTransInfoMapper.queryByChannel(channel);
        String[] arr = new String[3];
        if(list.isEmpty()){
            throw new TaoMiniException("无记录");
        }else if(list.size() == 1){

            AccountTransInfoDTO dto = list.get(0);
            arr[0] = dto.getCrtDate() + " " +dto.getCrtTime();
            arr[1] = dto.getMoney();
            arr[2] = String.valueOf(Double.parseDouble(dto.getMoney()) - Double.parseDouble(dto.getBeforeBalance()));

        }
        return arr;
    }

    private AccountInfoDTO getAccountInfo(String open_id, String channel){
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
        accountInfoDTO.setUser(open_id);
        accountInfoDTO.setChannel(channel);
        return accountInfoMapper.getAccountInfoByUserIdAndChannel(accountInfoDTO);
    }

    private void addAccountTransInfo(AccountInfoDTO accountInfoDTO, String oldBanlance){
        AccountTransInfoDTO accountTransInfoDTO = new AccountTransInfoDTO();
        accountTransInfoDTO.setAccountTransId(UUID.randomUUID().toString());
        accountTransInfoDTO.setChannel(accountInfoDTO.getChannel());
        accountTransInfoDTO.setChannelName(accountInfoDTO.getChannelName());
        accountTransInfoDTO.setCrtDate(DateUtil.getCurrDate());
        accountTransInfoDTO.setCrtTime(DateUtil.getCurrTime());
        accountTransInfoDTO.setUser(accountInfoDTO.getUser());
        accountTransInfoDTO.setMoney(accountInfoDTO.getBalance());
        accountTransInfoDTO.setBeforeBalance(oldBanlance);
        accountTransInfoMapper.save(accountTransInfoDTO);
    }
}