package com.taoboot.mini.controller;

import com.taoboot.mini.core.common.Result;
import com.taoboot.mini.core.common.TaoMiniException;
import com.taoboot.mini.model.AccountInfoDTO;
import com.taoboot.mini.model.AccountTransInfoDTO;
import com.taoboot.mini.service.IAccountInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author chentao
 * @create 2019/9/9
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    IAccountInfoService accountInfoService;

    @RequestMapping(value="upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(AccountInfoDTO accountInfoDTO){
        LOGGER.info("上传余额信息,{},{},{}", accountInfoDTO.getUser(), accountInfoDTO.getBalance(), accountInfoDTO.getChannelName());
        Result result = new Result();

        try{
            accountInfoService.saveOrUpdateAccountInfo(accountInfoDTO);
        }catch (Exception e){
            LOGGER.error("获取账户信息失败", e);
            result.setSucc(false);
            result.setRetMsg("交易异常");
        }

        return result;
    }

    @RequestMapping(value="/getAccountTransInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccountTransInfo(String length){
        LOGGER.info("获取上传余额交易记录:{}", length);
        Result result = new Result();

        try{
            List<AccountTransInfoDTO> retList = accountInfoService.getAccountTransInfo(length);
            LOGGER.info("返回查询上传余额条数:{}", retList.size());
            result.setData(retList);
            result.setSucc(true);
        }catch (Exception e){
            LOGGER.error("查询上传余额记录失败", e);
            result.setSucc(false);
            result.setRetMsg("交易失败");
        }

        return result;
    }

    @RequestMapping(value="/getAllAccountInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllAccountInfo(){
        Result result = new Result();

        try{
            result.setData(accountInfoService.getAccountInfo());
            result.setSucc(true);
        }catch (Exception e){
            LOGGER.error("获取所有账户信息失败", e);
            result.setSucc(false);
            result.setRetMsg("获取信息失败," + e.getMessage());
        }

        return result;
    }

    @RequestMapping(value="getAccountInfoByChannel", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccountInfoByChannel(String channel){
        LOGGER.info("获取渠道存款信息:{}", channel);
        Result result = new Result();

        try {
            result.setData(accountInfoService.getAccountInfoByChannel(channel));
            result.setSucc(true);
        } catch (TaoMiniException e) {
            LOGGER.error("查询出错:", e);
            result.setSucc(false);
        } catch (Exception e){
            LOGGER.error("查询出错:", e);
            result.setSucc(false);
        }

        return result;
    }

}